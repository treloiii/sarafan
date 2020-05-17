package com.trelloiii.sarafan.repository;

import com.trelloiii.sarafan.domain.Message;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @EntityGraph(value="Message.comments",type= EntityGraph.EntityGraphType.FETCH)
    List<Message> findAll();
}
