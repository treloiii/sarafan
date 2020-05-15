package com.trelloiii.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.exceptions.NotFoundException;
import com.trelloiii.sarafan.repository.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

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
        return message;
    }
    @PutMapping("{id}")
    public Message redactMessage(@PathVariable Long id,@RequestBody Message message){
        System.out.println(message.getText());
        Message messageOld=getMessage(id);
        BeanUtils.copyProperties(message,messageOld,"id");
        return messageRepository.save(messageOld);
    }
    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable Long id){
        messageRepository.delete(getMessage(id));
    }

}
