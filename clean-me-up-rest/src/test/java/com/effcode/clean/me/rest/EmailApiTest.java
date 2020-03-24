package com.effcode.clean.me.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmailApiTest {
    private static String VALID_REQUEST_URL = "/?adr=kalle@korv.com&subject=Hello&content=Hello+everybody!";

    @Autowired
    private EmailApi emailApi;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(emailApi).isNotNull();
    }

    @Test
    void happyPath() throws Exception {
        mockMvc.perform(post(VALID_REQUEST_URL)).andExpect(status().isOk());
    }

    @Test
    void shouldOnlyAcceptPostRequests() throws Exception {
        mockMvc.perform(delete(VALID_REQUEST_URL)).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(get(VALID_REQUEST_URL)).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(head(VALID_REQUEST_URL)).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(patch(VALID_REQUEST_URL)).andExpect(status().isMethodNotAllowed());
        mockMvc.perform(put(VALID_REQUEST_URL)).andExpect(status().isMethodNotAllowed());
    }
}
