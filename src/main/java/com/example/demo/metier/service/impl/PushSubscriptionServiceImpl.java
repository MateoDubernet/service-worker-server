package com.example.demo.metier.service.impl;

import com.example.demo.metier.PushNotificationContent;
import com.example.demo.metier.PushSubscription;
import com.example.demo.metier.PushSubscriptionEntity;
import com.example.demo.metier.repo.PushSubscriptionRepo;
import com.example.demo.metier.service.PushSubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class PushSubscriptionServiceImpl implements PushSubscriptionService {

    private static final String PUBLIC_KEY = "BBGknvdFcCC-ibeA7o-Bd9d7cqbUG5pS40BEqfXOCbPW0ePkhojfgweiFjDcep_S8WXYqNPMYDEHAHWWYj0StPE";
    private static final String PRIVATE_KEY = "J1B5ABAy8qCzAMrxoZWaAb028RzMIJBPeGxJvyZ02-c";

    private static final PushService pushService = new PushService();
    private final PushSubscriptionRepo pushSubscriptionRepo;

    public PushSubscriptionServiceImpl(PushSubscriptionRepo pushSubscriptionRepo) {
        this.pushSubscriptionRepo = pushSubscriptionRepo;
    }

    @Override
    public String getPublicKey() {
        return PUBLIC_KEY;
    }

    @Override
    public void sendNotification(PushNotificationContent message) throws GeneralSecurityException, JoseException, IOException, ExecutionException, InterruptedException {

        pushService.setPublicKey(PUBLIC_KEY);
        pushService.setPrivateKey(PRIVATE_KEY);

        String payload = new ObjectMapper().writeValueAsString(message);

        List<PushSubscriptionEntity> pushSubscriptions = pushSubscriptionRepo.findAll();

        for (PushSubscriptionEntity pushSubscription: pushSubscriptions) {
            Notification notification = new Notification(
                    pushSubscription.getEndpoint(),
                    pushSubscription.getP256dhKey(),
                    pushSubscription.getAuthKey(),
                    payload.getBytes(),
                    60
            );

            pushService.send(notification);
        }
    }

    @Override
    public void saveSubscription(PushSubscription subscription) {

        PushSubscriptionEntity pushSubscriptionEntity = new PushSubscriptionEntity();
        pushSubscriptionEntity.setEndpoint(subscription.getEndpoint());
        pushSubscriptionEntity.setP256dhKey(subscription.getKeys().get("p256dh"));
        pushSubscriptionEntity.setAuthKey(subscription.getKeys().get("auth"));

        Optional<PushSubscriptionEntity> existingPushSubscription = pushSubscriptionRepo.findByEndpoint(pushSubscriptionEntity.getEndpoint());
        existingPushSubscription.ifPresent(subscriptionEntity -> pushSubscriptionEntity.setId(subscriptionEntity.getId()));

        pushSubscriptionRepo.save(pushSubscriptionEntity);
    }
}
