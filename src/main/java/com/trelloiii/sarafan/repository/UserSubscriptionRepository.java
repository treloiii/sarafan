package com.trelloiii.sarafan.repository;

import com.trelloiii.sarafan.domain.User;
import com.trelloiii.sarafan.domain.UserSubscription;
import com.trelloiii.sarafan.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
}
