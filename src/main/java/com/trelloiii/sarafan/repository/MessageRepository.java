package com.trelloiii.sarafan.repository;

import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @EntityGraph(value = "Message.comments", type = EntityGraph.EntityGraphType.FETCH)
    Page<Message> findByAuthorIn(List<User> authors, Pageable page);
}
