package org.systems.urlshortner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.systems.urlshortner.exceptions.BadRequestException;
import org.systems.urlshortner.exceptions.ResourceNotFoundException;
import org.systems.urlshortner.model.URLPayload;
import org.systems.urlshortner.services.URLServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class URLController {

    private final URLServiceImpl originalURLService;

    @Autowired
    public URLController(URLServiceImpl originalURLService) {
        this.originalURLService = originalURLService;
    }

    // Create short URL from original
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody URLPayload originalUrl) {
        if (originalUrl == null || originalUrl.getOriginalUrl() == null || originalUrl.getOriginalUrl().isBlank()) {
            throw new BadRequestException("Original URL must not be empty.");
        }
        return ResponseEntity.ok(
                originalURLService.saveOriginalURL(originalUrl).getShortenedUrl()
        );
    }

    // Create short URL with custom alias
    @PostMapping("/shorten/custom")
    public ResponseEntity<String> shortenWithCustomAlias(@RequestParam String customAlias,
                                                         @RequestBody URLPayload originalUrl) {
        if (customAlias == null || customAlias.isBlank()) {
            throw new BadRequestException("Custom alias must not be empty.");
        }
        if (originalUrl == null || originalUrl.getOriginalUrl() == null || originalUrl.getOriginalUrl().isBlank()) {
            throw new BadRequestException("Original URL must not be empty.");
        }
        return ResponseEntity.ok(
                originalURLService.saveOriginalURLWithCustomAlias(originalUrl, customAlias).getShortenedUrl()
        );
    }

    // Redirect short URL to original
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        URLPayload urlPayload = originalURLService.getOriginalURL("localhost/" + shortUrl);
        if (urlPayload == null) {
            throw new ResourceNotFoundException("Short URL not found: " + shortUrl);
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlPayload.getOriginalUrl()))
                .build();
    }

    // Get statistics for a short URL (click count, created date, expiry)
    @GetMapping("/stats/{shortUrl}")
    public ResponseEntity<Map<String, Object>> getStatistics(@PathVariable String shortUrl) {
        Map<String, Object> stats = originalURLService.getUrlStats(shortUrl);
        if (stats == null || stats.isEmpty()) {
            throw new ResourceNotFoundException("Statistics not found for short URL: " + shortUrl);
        }
        return ResponseEntity.ok(stats);
    }

    // List all shortened URLs
    @GetMapping("/all")
    public ResponseEntity<List<URLPayload>> getAllUrls() {
        List<URLPayload> urls = originalURLService.getAllUrls();
        if (urls == null || urls.isEmpty()) {
            throw new ResourceNotFoundException("No shortened URLs found.");
        }
        return ResponseEntity.ok(urls);
    }

    // Delete a shortened URL
    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<String> deleteShortUrl(@PathVariable String shortUrl) {
        boolean deleted = originalURLService.deleteUrl(shortUrl);
        if (!deleted) {
            throw new ResourceNotFoundException("URL not found: " + shortUrl);
        }
        return ResponseEntity.ok("URL deleted successfully.");
    }
}
