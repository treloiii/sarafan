package com.trelloiii.sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@NamedEntityGraph(
        name = "User.subs",
        attributeNodes = {
                @NamedAttributeNode("subscriptions"),
                @NamedAttributeNode("subscribers")
        }
        )
@EqualsAndHashCode(of = "id")
@ToString(of={"id","name"})
public class User implements Serializable {
    @Id
    @JsonView(Views.IdName.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.IdName.class)
    private String userpic;
    @JsonView(Views.FullData.class)
    private String email;
    @JsonView(Views.FullData.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullData.class)
    private LocalDateTime lastVisit;

    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    @JsonView(Views.FullData.class)
    private Set<UserSubscription> subscriptions;

    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonView(Views.FullData.class)
    private Set<UserSubscription> subscribers;
}
