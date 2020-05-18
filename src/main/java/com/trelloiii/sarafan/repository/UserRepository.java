package com.trelloiii.sarafan.repository;

import com.trelloiii.sarafan.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    @EntityGraph(value = "User.subs", type = EntityGraph.EntityGraphType.LOAD)
//    @Query("select User from User us join fetch us.subscribers where us.id=:id ")
    Optional<User> findById(String s);
}
