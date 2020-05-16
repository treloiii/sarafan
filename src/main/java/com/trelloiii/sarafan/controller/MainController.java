package com.trelloiii.sarafan.controller;

import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private MessageRepository messageRepository;
    @Value("${spring.profiles.active}")
    private String profile;
    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user){
        HashMap<Object,Object> data=new HashMap<>();
        data.put("profile",user);
        data.put("messages",messageRepository.findAll());
        model.addAttribute("frontendData",data);
        model.addAttribute("isDevMode","dev".equals(profile));
        return "index";
    }
}
