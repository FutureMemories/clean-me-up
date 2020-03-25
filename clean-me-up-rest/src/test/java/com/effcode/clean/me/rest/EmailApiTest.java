package com.effcode.clean.me.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmailApi.class)
class EmailApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailHandler emailHandler;

    private MockHttpServletRequestBuilder addValidRequestParams(MockHttpServletRequestBuilder builder) {
        return builder.param("adr", "anna@beta.com")
                .param("subject", "Hello")
                .param("content", "Hello, world!");
    }

    @Test
    void happyPath() throws Exception {
        mockMvc.perform(addValidRequestParams(post("/"))).andExpect(status().isOk());
    }

    @Test
    void shouldOnlyAcceptPostRequests() throws Exception {
        mockMvc.perform(addValidRequestParams(delete("/"))).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(addValidRequestParams(get("/"))).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(addValidRequestParams(head("/"))).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(addValidRequestParams(patch("/"))).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(addValidRequestParams(put("/"))).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldValidateEmailParameter() throws Exception {
        MockHttpServletRequestBuilder badEmailRequest = post("/")
                .param("adr", "not-an-email")
                .param("subject", "Hello")
                .param("content", "Hello, world!");

        mockMvc.perform(badEmailRequest)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(containsString("must be a well-formed email address")));
    }

    @Test
    void shouldValidateSubjectParameter() throws Exception {
        MockHttpServletRequestBuilder nullSubjectRequest = post("/")
                .param("adr", "anna@beta.com")
                .param("subject", "")
                .param("content", "Hello, world!");

        mockMvc.perform(nullSubjectRequest)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(containsString("must not be blank")));
    }

    @Test
    void shouldValidateContentParameter() throws Exception {
        StringBuilder contentBuffer = new StringBuilder();
        for (int i = 0; i < EmailApi.MAX_CONTENT_LENGTH; i++) {
            contentBuffer.append(".");
        }
        String maxLengthContent = contentBuffer.toString();

        MockHttpServletRequestBuilder maxLengthContentRequest = post("/")
                .param("adr", "anna@beta.com")
                .param("subject", "Hello")
                .param("content", maxLengthContent);

        mockMvc.perform(maxLengthContentRequest).andExpect(status().isOk());

        MockHttpServletRequestBuilder contentTooLongRequest = post("/")
                .param("adr", "anna@beta.com")
                .param("subject", "Hello")
                .param("content", maxLengthContent + "!");

        mockMvc.perform(contentTooLongRequest)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(containsString("size must be between " + EmailApi.MIN_CONTENT_LENGTH + " and " + EmailApi.MAX_CONTENT_LENGTH)));
    }
}
