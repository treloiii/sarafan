package com.trelloiii.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.dto.EventType;
import com.trelloiii.sarafan.dto.MetaDto;
import com.trelloiii.sarafan.dto.ObjectType;
import com.trelloiii.sarafan.exceptions.NotFoundException;
import com.trelloiii.sarafan.repository.MessageRepository;
import com.trelloiii.sarafan.util.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/message")
public class MessageController {
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageRepository messageRepository;
    private final BiConsumer<EventType,Message> wsSender;

    @Autowired
    public MessageController(MessageRepository messageRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE,Views.FullMessage.class);
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list(){
        return messageRepository.findAll();
    }
    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getMessage(@PathVariable Long id){
        return messageRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    @PostMapping
    public Message newMessage(@RequestBody Message message, @AuthenticationPrincipal User user) throws IOException {
        message.setCreationTime(LocalDateTime.now());
        message.setAuthor(user );
        fillMetadata(message);
        messageRepository.save(message);
        wsSender.accept(EventType.CREATE,message);
        return message;
    }
    @PutMapping("{id}")
    public Message redactMessage(@PathVariable Long id,@RequestBody Message message) throws IOException {
        System.out.println(message.getText());
        Message messageOld=getMessage(id);
        BeanUtils.copyProperties(message,messageOld,"id");
        fillMetadata(messageOld);
        wsSender.accept(EventType.UPDATE,messageOld);
        return messageRepository.save(messageOld);
    }
    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable Long id){
        Message message=getMessage(id);
        messageRepository.delete(message);
        wsSender.accept(EventType.REMOVE,message);
    }

    private void fillMetadata(Message message) throws IOException {
        String text=message.getText();
        Matcher matcher=URL_REGEX.matcher(text);//get link matcher;

        if(matcher.find()){
           String url=text.substring(matcher.start(),matcher.end());
           message.setText(text.replace(url,""));
           message.setLink(url);
           matcher=IMG_REGEX.matcher(url);
           if(matcher.find()){
               message.setLinkCover(url);
           }else if(!url.contains("youtu")){
               MetaDto meta=getMeta(url);
               message.setLinkTitle(meta.getTitle());
               message.setLinkCover(meta.getCover());
               message.setLinkDescription(meta.getDescription());
           }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc=Jsoup.connect(url).get();
        Elements title=doc.select("[name$=title],[property$=title]");
        Elements description=doc.select("[name$=description],[property$=description]");
        Elements cover=doc.select("[name$=image],[property$=image]");
        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first()));
    }

    private String getContent(Element el){
        return el==null?"":el.attr("content");
    }

}
