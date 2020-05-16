package com.trelloiii.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.dto.EventType;
import com.trelloiii.sarafan.dto.ObjectType;
import com.trelloiii.sarafan.exceptions.NotFoundException;
import com.trelloiii.sarafan.repository.MessageRepository;
import com.trelloiii.sarafan.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageRepository messageRepository;
    private final BiConsumer<EventType,Message> wsSender;

    @Autowired
    public MessageController(MessageRepository messageRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE,Views.IdName.class);
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
    public Message newMessage(@RequestBody Message message){
        message.setCreationTime(LocalDateTime.now());
        messageRepository.save(message);
        wsSender.accept(EventType.CREATE,message);
        return message;
    }
    @PutMapping("{id}")
    public Message redactMessage(@PathVariable Long id,@RequestBody Message message){
        System.out.println(message.getText());
        Message messageOld=getMessage(id);
        BeanUtils.copyProperties(message,messageOld,"id");
        wsSender.accept(EventType.UPDATE,messageOld);
        return messageRepository.save(messageOld);
    }
    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable Long id){
        Message message=getMessage(id);
        messageRepository.delete(message);
        wsSender.accept(EventType.REMOVE,message);
    }

}
