package com.trelloiii.sarafan.service;

import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfileService {
    @Autowired
    private UserRepository userRepository;

    public User changeSubscription(User channel, User subscriber) {
        Set<User> subscribers=channel.getSubscribers();
        if(subscribers.contains(subscriber)){
            subscribers.remove(subscriber);
        }else{
            subscribers.add(subscriber);
        }
        return userRepository.save(channel);
    }
}
