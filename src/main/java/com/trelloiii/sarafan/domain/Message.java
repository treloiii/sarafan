package com.trelloiii.sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.*;

@Data
@Entity
@Table
@ToString(of={"id","text"})
@EqualsAndHashCode(of={"id"})
@NamedEntityGraph(name = "Message.comments",attributeNodes = @NamedAttributeNode("comments"))
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;
    @Column(updatable = false)
    @JsonView(Views.FullData.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.IdName.class)
    private User author;
    @OneToMany(mappedBy = "message",orphanRemoval = true)
    @JsonView(Views.FullData.class)
    private List<Comment> comments;

    @JsonView(Views.IdName.class)
    private String link;
    @JsonView(Views.IdName.class)
    private String linkTitle;
    @JsonView(Views.IdName.class)
    private String linkDescription;
    @JsonView(Views.IdName.class)
    private String linkCover;


    public Message() {
    }

    public Message(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
