package com.trelloiii.sarafan.service;

import com.trelloiii.sarafan.domain.Comment;
import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.dto.EventType;
import com.trelloiii.sarafan.dto.ObjectType;
import com.trelloiii.sarafan.repository.CommentRepository;
import com.trelloiii.sarafan.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    public CommentService(WsSender wsSender) {
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user){
        comment.setUser(user);
        Comment commentFromDb=commentRepository.save(comment);
        wsSender.accept(EventType.CREATE,commentFromDb);
        return commentFromDb;
    }
}
