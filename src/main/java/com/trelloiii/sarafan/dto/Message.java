package com.trelloiii.sarafan.dto;

import lombok.Data;

@Data
public class Message {
    private Long id;
    private String text;
    public Message(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
