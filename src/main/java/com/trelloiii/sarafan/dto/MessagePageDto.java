package com.trelloiii.sarafan.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    private List<Message> messagesList;
    private int currentPage;
    private int totalPages;
}
