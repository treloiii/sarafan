package com.trelloiii.sarafan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.dto.MessagePageDto;
import com.trelloiii.sarafan.repository.UserRepository;
import com.trelloiii.sarafan.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final UserRepository userRepository;
    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;

    public MainController(MessageService messageRepository, UserRepository userRepository, ObjectMapper mapper) {
        this.messageService = messageRepository;
        this.userRepository = userRepository;
        mapper.setConfig(mapper.getSerializationConfig());
        this.messageWriter = mapper.writerWithView(Views.FullMessage.class);
        this.profileWriter = mapper.writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        String messages = "null";
        String serializedUser="null";
        if (user != null) {
            User userFromDb=userRepository.findById(user.getId()).get();
            serializedUser=profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", serializedUser);
            PageRequest pageRequest = PageRequest.of(
                    0,
                    MessageController.MESSAGE_PER_PAGE,
                    Sort.by(Sort.Direction.DESC, "id")
            );
            MessagePageDto messageDto = messageService.findAll(pageRequest);
            messages = messageWriter.writeValueAsString(messageDto.getMessagesList());
            data.put("currentPage",messageDto.getCurrentPage());
            data.put("totalPages",messageDto.getTotalPages());
        }
        model.addAttribute("profile", serializedUser);
        model.addAttribute("messages", messages);
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
