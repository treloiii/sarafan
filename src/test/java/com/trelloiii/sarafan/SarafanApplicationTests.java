package com.trelloiii.sarafan;

import com.trelloiii.sarafan.domain.Comment;
import com.trelloiii.sarafan.domain.Message;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.repository.CommentRepository;
import com.trelloiii.sarafan.repository.MessageRepository;
import com.trelloiii.sarafan.repository.UserRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log
class SarafanApplicationTests {

}
