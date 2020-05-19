package com.trelloiii.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.Views;
import com.trelloiii.sarafan.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("{id}")
    @JsonView(Views.FullData.class)
    public User get(@PathVariable("id") User user){
        return user;
    }

    @PostMapping("/change-subscription/{channelId}")
    public User changeSubscription(
            @AuthenticationPrincipal User subscriber,
            @PathVariable("channelId") User channel
    ){
        if(subscriber.equals(channel)){
            return channel;
        }
        else{
            return profileService.changeSubscription(channel,subscriber);
        }
    }



}
