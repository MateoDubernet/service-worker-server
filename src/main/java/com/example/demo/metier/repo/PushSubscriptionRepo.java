package com.example.demo.metier.repo;

import com.example.demo.metier.PushSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PushSubscriptionRepo extends JpaRepository<PushSubscriptionEntity, Long> {
    Optional<PushSubscriptionEntity> findByEndpoint(String endpoint);
}
