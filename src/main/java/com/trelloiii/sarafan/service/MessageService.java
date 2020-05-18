package com.trelloiii.sarafan.service;

import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.dto.MessagePageDto;
import com.trelloiii.sarafan.dto.MetaDto;
import com.trelloiii.sarafan.exceptions.NotFoundException;
import com.trelloiii.sarafan.repository.MessageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);
    @Autowired
    private MessageRepository messageRepository;

    public Message getMessage(Long id) {
        return messageRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public MessagePageDto findAll(Pageable pageable) {
        Page<Message> messagePage = messageRepository.findAll(pageable);
        return new MessagePageDto(
                messagePage.getContent(),
                pageable.getPageNumber(),
                messagePage.getTotalPages()
        );
    }

    public Message save(User user, Message message) throws IOException {
        message.setCreationTime(LocalDateTime.now());
        message.setAuthor(user);
        fillMetadata(message);
        return messageRepository.save(message);
    }

    private void fillMetadata(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);//get link matcher;

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());
            message.setText(text.replace(url, ""));
            message.setLink(url);
            matcher = IMG_REGEX.matcher(url);
            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);
                message.setLinkTitle(meta.getTitle());
                message.setLinkCover(meta.getCover());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements title = doc.select("[name$=title],[property$=title]");
        Elements description = doc.select("[name$=description],[property$=description]");
        Elements cover = doc.select("[name$=image],[property$=image]");
        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first()));
    }

    private String getContent(Element el) {
        return el == null ? "" : el.attr("content");
    }

    public Message save(Long id, Message message) throws IOException {
        Message messageOld = getMessage(id);
        messageOld.setText(message.getText());
//      BeanUtils.copyProperties(message,messageOld,"id");
        fillMetadata(messageOld);
        return messageRepository.save(messageOld);
    }

    public Message delete(Long id) {
        Message message = getMessage(id);
        messageRepository.delete(message);
        return message;
    }
}
