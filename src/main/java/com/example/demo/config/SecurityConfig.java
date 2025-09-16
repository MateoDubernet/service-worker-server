package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.Security;

@Slf4j
@Configuration
public class SecurityConfig {
    @PostConstruct
    public void init() {
        Security.addProvider(new BouncyCastleProvider());
    }
}
