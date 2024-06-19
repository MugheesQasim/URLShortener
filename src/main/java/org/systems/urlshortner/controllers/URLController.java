package org.systems.urlshortner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.systems.urlshortner.model.URLPayload;
import org.systems.urlshortner.services.URLServiceImpl;

import java.net.URI;

@RestController
@RequestMapping()
public class URLController {
    private final URLServiceImpl originalURLService;

    @Autowired
    public URLController(URLServiceImpl originalURLService) {
        this.originalURLService = originalURLService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody URLPayload originalUrl) {
        return new ResponseEntity<>(originalURLService.saveOriginalURL(originalUrl).getShortenedUrl(), HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        URLPayload urlPayload = originalURLService.getOriginalURL("localhost/" + shortUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlPayload.getOriginalUrl()))
                .build();
    }
}
