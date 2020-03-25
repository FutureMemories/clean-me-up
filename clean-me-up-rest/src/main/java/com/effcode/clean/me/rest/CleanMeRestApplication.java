package com.effcode.clean.me.rest;

import com.effcode.clean.me.support.SmtpHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class CleanMeRestApplication {
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(CleanMeRestApplication.class, args);
    }

    @Bean
    public SmtpHandler smtpHandler() {
        return new SmtpHandler();
    }

    @Bean
    public EmailHandlerCredentials emailHandlerCredentials() {
        return new EmailHandlerCredentials() {
            @Override
            public String getUsername() {
                return env.getProperty("com.effcode.clean.me.rest.emailhandlercredentials.username");
            }

            @Override
            public String getPassword() {
                return env.getProperty("com.effcode.clean.me.rest.emailhandlercredentials.password");
            }
        };
    }
}
