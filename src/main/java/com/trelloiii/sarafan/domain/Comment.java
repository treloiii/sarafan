package com.trelloiii.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.MessageMainInfo.class)
    private Long id;
    @JsonView(Views.MessageMainInfo.class)
    private String text;
    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(Views.FullComment.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false,updatable = false)
    @JsonView(Views.MessageMainInfo.class)
    private User user;
}
