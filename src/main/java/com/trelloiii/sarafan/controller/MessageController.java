package com.trelloiii.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.dto.EventType;
import com.trelloiii.sarafan.dto.MessagePageDto;
import com.trelloiii.sarafan.dto.ObjectType;
import com.trelloiii.sarafan.service.MessageService;
import com.trelloiii.sarafan.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/message")
public class MessageController {
    public final static int MESSAGE_PER_PAGE=3;
    private final MessageService messageService;
    private final BiConsumer<EventType, Message> wsSender;

    @Autowired
    public MessageController(MessageService messageService, WsSender wsSender) {
        this.messageService = messageService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
    }

    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto list(@PageableDefault(size = MESSAGE_PER_PAGE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        MessagePageDto messagePageDto=messageService.findAll(pageable);
        return messagePageDto;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getMessage(@PathVariable Long id) {
        return messageService.getMessage(id);
    }

    @PostMapping
    public Message newMessage(@RequestBody Message message, @AuthenticationPrincipal User user) throws IOException {
        messageService.save(user, message);
        wsSender.accept(EventType.CREATE, message);
        return message;
    }

    @PutMapping("{id}")
    public Message redactMessage(@PathVariable Long id, @RequestBody Message message) throws IOException {
        Message saved = messageService.save(id, message);
        wsSender.accept(EventType.UPDATE, saved);
        return saved;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable Long id) {
        Message message = messageService.delete(id);
        wsSender.accept(EventType.REMOVE, message);
    }

}
