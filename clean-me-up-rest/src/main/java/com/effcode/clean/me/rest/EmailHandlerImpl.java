package com.effcode.clean.me.rest;

import com.effcode.clean.me.support.SmtpEmail;
import com.effcode.clean.me.support.SmtpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailHandlerImpl implements EmailHandler {
    private final SmtpHandler smtpHandler;
    private final EmailHandlerCredentials emailHandlerCredentials;
    private final Logger log = LoggerFactory.getLogger(EmailHandlerImpl.class);

    public EmailHandlerImpl(final SmtpHandler smtpHandler, final EmailHandlerCredentials emailHandlerCredentials) {
        this.smtpHandler = smtpHandler;
        this.emailHandlerCredentials = emailHandlerCredentials;
    }

    public void send(final String emailAddress, final String subject, final String content) {
        SmtpEmail smtpEmail = new SmtpEmail();
        smtpEmail.adrs = new String[]{emailAddress};
        smtpEmail.content = content;
        smtpEmail.subject = subject;
        smtpEmail.password = this.emailHandlerCredentials.getPassword();
        smtpEmail.username = this.emailHandlerCredentials.getUsername();
        smtpHandler.post(smtpEmail);
        log.info("Send email. Adr: " + emailAddress + ", Subject: " + subject);
    }
}
