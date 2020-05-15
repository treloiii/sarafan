package com.trelloiii.sarafan.controller;

import com.trelloiii.sarafan.dto.Message;
import com.trelloiii.sarafan.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private List<Message> messages=new ArrayList<>();
    {
        for(int i=0;i<10;i++){
            messages.add(new Message((long) i,"Message #"+i));
        }
    }
    @GetMapping
    public List<Message> list(){
        System.out.println(messages);
        return messages;
    }
    @GetMapping("{id}")
    public Message getMessage(@PathVariable Long id){
        return messages.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
    @PostMapping
    public Message newMessage(@RequestBody Message message){
        messages.add(message);
        return message;
    }
    @PutMapping("{id}")
    public Message redactMessage(@PathVariable Long id,@RequestBody Message message){
        System.out.println(message.getText());
        Message messageOld=messages.stream()
                .filter(m->m.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        int index=messages.indexOf(messageOld);
        messages.set(index,message);
        return message;
    }
    @DeleteMapping("{id}")
    public Message deleteMessage(@PathVariable Long id){
        Message deleted=messages.stream().filter(m->m.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        messages.remove(deleted);
        return deleted;
    }

}
