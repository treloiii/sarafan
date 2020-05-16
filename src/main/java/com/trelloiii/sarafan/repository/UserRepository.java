package com.trelloiii.sarafan.repository;

import com.trelloiii.sarafan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
