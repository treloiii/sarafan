package com.trelloiii.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trelloiii.sarafan.domain.Comment;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.service.CommentService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.NotActiveException;

@RestController
@RequestMapping("/comment")
@Log
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    @JsonView(Views.IdName.class)
    public Comment add(@RequestBody Comment comment, @AuthenticationPrincipal User user){
        return commentService.create(comment,user);
    }
    @GetMapping("{id}")
    @JsonView(Views.IdName.class)
    public Comment getId(@PathVariable Long id) throws NotActiveException {
        return commentService.getById(id);
    }
}
