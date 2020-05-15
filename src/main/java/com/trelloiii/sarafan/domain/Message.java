package com.trelloiii.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@ToString(of={"id","text"})
@EqualsAndHashCode(of={"id"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;
    @Column(updatable = false)
    @JsonView(Views.FullMessage.class)
    private LocalDateTime creationTime;

    public Message() {
    }

    public Message(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
