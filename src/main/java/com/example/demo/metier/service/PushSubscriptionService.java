package com.example.demo.metier.service;

import com.example.demo.metier.PushNotificationContent;
import com.example.demo.metier.PushSubscription;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

public interface PushSubscriptionService {
    String getPublicKey();
    void sendNotification(PushNotificationContent message) throws GeneralSecurityException, JoseException, IOException, ExecutionException, InterruptedException;
    void saveSubscription(PushSubscription subscription);
}
