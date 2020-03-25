package com.effcode.clean.me.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Controller
@Validated
public class EmailApi {
    public static final int MAX_CONTENT_LENGTH = 65000;
    public static final int MIN_CONTENT_LENGTH = 0;

    private final EmailHandler emailHandler;

    public EmailApi(EmailHandler emailHandler) {
        this.emailHandler = emailHandler;
    }

    @PostMapping("/")
    public ResponseEntity<Void> send(
            @RequestParam(name = "adr") @NotNull @Email String emailAddress,
            @RequestParam @NotBlank String subject,
            @RequestParam @NotNull @Size(min = MIN_CONTENT_LENGTH, max = MAX_CONTENT_LENGTH) String content) {
        emailHandler.send(emailAddress, subject, content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
