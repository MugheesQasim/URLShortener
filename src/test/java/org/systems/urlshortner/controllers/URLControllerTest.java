package org.systems.urlshortner.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.systems.urlshortner.model.URLPayload;
import org.systems.urlshortner.services.URLServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = URLController.class)
class URLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLServiceImpl urlService; // fully mocked, no DB calls

    @Test
    void shortenUrl() throws Exception {
        URLPayload mockPayload = new URLPayload();
        mockPayload.setOriginalUrl("https://example.com");
        mockPayload.setShortenedUrl("http://localhost/short123");

        when(urlService.saveOriginalURL(any(URLPayload.class))).thenReturn(mockPayload);

        String requestBody = "{ \"originalUrl\": \"https://example.com\" }";

        mockMvc.perform(post("/api/shorten") // FIX: add /api prefix
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("http://localhost/short123"));

        verify(urlService, times(1)).saveOriginalURL(any(URLPayload.class));
    }

    @Test
    void redirect() throws Exception {
        URLPayload mockPayload = new URLPayload();
        mockPayload.setOriginalUrl("https://example.com");

        when(urlService.getOriginalURL("localhost/short123")).thenReturn(mockPayload);

        mockMvc.perform(get("/api/short123")) // FIX: add /api prefix
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://example.com"));

        verify(urlService, times(1)).getOriginalURL("localhost/short123");
    }
}
