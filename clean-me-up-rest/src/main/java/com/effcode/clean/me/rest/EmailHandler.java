package com.effcode.clean.me.rest;

public interface EmailHandler {
    void send(final String emailAddress, final String subject, final String content);
}
