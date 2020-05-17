package com.trelloiii.sarafan.service;

import com.trelloiii.sarafan.domain.Comment;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    public Comment create(Comment comment, User user){
        comment.setUser(user);
        return commentRepository.save(comment);
    }
}
