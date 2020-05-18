package com.trelloiii.sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
@Data
public class User implements Serializable {
    @Id
    @JsonView(Views.MessageMainInfo.class)
    private String id;
    @JsonView(Views.MessageMainInfo.class)
    private String name;
    @JsonView(Views.MessageMainInfo.class)
    private String userpic;
    @JsonView(Views.FullUser.class)
    private String email;
    @JsonView(Views.FullUser.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;
}
