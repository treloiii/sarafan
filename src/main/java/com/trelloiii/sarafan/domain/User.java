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
    @JsonView(Views.FullProfile.class)
    private String email;
    @JsonView(Views.FullProfile.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullProfile.class)
    private LocalDateTime lastVisit;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscribptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @JsonView(Views.FullProfile.class)
    private Set<User> subscriptions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference
    @JoinTable(
            name = "user_subscribptions",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @JsonView(Views.FullProfile.class)
    private Set<User> subscribers;
}
