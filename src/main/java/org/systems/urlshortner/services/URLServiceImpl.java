package org.systems.urlshortner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.systems.urlshortner.model.URLPayload;
import org.systems.urlshortner.repositories.URLRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class URLServiceImpl implements URLService {

    private final URLRepository urlRepository;
    private final ShortURLGeneratorServiceImpl shortURLGeneratorService;

    @Autowired
    public URLServiceImpl(URLRepository urlRepository, ShortURLGeneratorServiceImpl shortURLGeneratorService) {
        this.urlRepository = urlRepository;
        this.shortURLGeneratorService = shortURLGeneratorService;
    }

    @Override
    public URLPayload saveOriginalURL(URLPayload urlPayload) {
        String shortenedUrl;

        do {
            shortenedUrl = shortURLGeneratorService.generate();
        } while (checkIfShortUrlExists(shortenedUrl));

        urlPayload.setShortenedUrl(shortenedUrl);
        urlPayload.setCreatedAt(LocalDateTime.now());
        urlPayload.setClickCount(0);
        return urlRepository.save(urlPayload);
    }

    public URLPayload saveOriginalURLWithCustomAlias(URLPayload urlPayload, String customAlias) {
        if (checkIfShortUrlExists(customAlias)) {
            throw new IllegalArgumentException("Custom alias already exists: " + customAlias);
        }
        urlPayload.setShortenedUrl(customAlias);
        urlPayload.setCreatedAt(LocalDateTime.now());
        urlPayload.setClickCount(0);
        return urlRepository.save(urlPayload);
    }

    @Override
    public URLPayload getOriginalURL(String shortUrl) {
        List<URLPayload> urlPayloadList = urlRepository.findByShortUrl(shortUrl);

        if (urlPayloadList != null && !urlPayloadList.isEmpty()) {
            URLPayload payload = urlPayloadList.getFirst();

            // Expiration check
            if (payload.getExpiresAt() != null && LocalDateTime.now().isAfter(payload.getExpiresAt())) {
                throw new IllegalStateException("This URL has expired.");
            }

            // Update stats
            payload.setClickCount(payload.getClickCount() + 1);
            payload.setLastAccessed(LocalDateTime.now());
            urlRepository.save(payload);

            return payload;
        } else {
            return null;
        }
    }

    public Map<String, Object> getUrlStats(String shortUrl) {
        List<URLPayload> urlPayloadList = urlRepository.findByShortUrl(shortUrl);
        if (urlPayloadList == null || urlPayloadList.isEmpty()) {
            return Map.of("error", "Short URL not found");
        }

        URLPayload payload = urlPayloadList.getFirst();

        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("originalUrl", payload.getOriginalUrl());
        stats.put("shortUrl", payload.getShortenedUrl());
        stats.put("clickCount", payload.getClickCount());
        stats.put("createdAt", payload.getCreatedAt());
        stats.put("lastAccessed", payload.getLastAccessed());
        stats.put("expiresAt", payload.getExpiresAt());

        return stats;
    }

    public List<URLPayload> getAllUrls() {
        return urlRepository.findAll();
    }

    public boolean deleteUrl(String shortUrl) {
        List<URLPayload> list = urlRepository.findByShortUrl(shortUrl);
        if (list != null && !list.isEmpty()) {
            urlRepository.delete(list.getFirst());
            return true;
        }
        return false;
    }

    public List<URLPayload> bulkShorten(List<URLPayload> urls) {
        List<URLPayload> savedUrls = new ArrayList<>();
        for (URLPayload url : urls) {
            savedUrls.add(saveOriginalURL(url));
        }
        return savedUrls;
    }

    private boolean checkIfShortUrlExists(String shortenedUrl) {
        List<URLPayload> urlPayloadList = urlRepository.findByShortUrl(shortenedUrl);
        return urlPayloadList != null && !urlPayloadList.isEmpty() && urlPayloadList.getFirst() != null;
    }
}
