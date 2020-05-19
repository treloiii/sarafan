package com.trelloiii.sarafan.service;

import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.UserSubscription;
import com.trelloiii.sarafan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private UserRepository userRepository;

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subs=channel.getSubscribers()
                .stream()
                .filter(subscription->subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());
        if(subs.isEmpty()){
            UserSubscription subscription=new UserSubscription(channel,subscriber);
            channel.getSubscribers().add(subscription);
        }else{
            channel.getSubscribers().removeAll(subs);
        }
        return userRepository.save(channel);
    }
}
