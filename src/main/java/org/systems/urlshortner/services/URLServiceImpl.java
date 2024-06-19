package org.systems.urlshortner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.systems.urlshortner.model.URLPayload;
import org.systems.urlshortner.repositories.URLRepository;

import java.util.List;

@Service
public class URLServiceImpl implements URLService {
    private final URLRepository urlRepository;
    private final ShortURLGeneratorServiceImpl shortURLGeneratorService;

    @Autowired
    public URLServiceImpl(URLRepository urlRepository, ShortURLGeneratorServiceImpl ShortURLGeneratorService) {
        this.urlRepository = urlRepository;
        this.shortURLGeneratorService = ShortURLGeneratorService;
    }

    @Override
    public URLPayload saveOriginalURL(URLPayload urlPayload) {
        String shortenedUrl;

        do {
            shortenedUrl = shortURLGeneratorService.generate();
        } while (checkIfShortUrlExists(shortenedUrl));

        urlPayload.setShortenedUrl(shortenedUrl);
        return urlRepository.save(urlPayload);
    }

    @Override
    public URLPayload getOriginalURL(String shortUrl) {
        List<URLPayload> urlPayloadList = urlRepository.findByShortUrl(shortUrl);

        if (urlPayloadList != null && !urlPayloadList.isEmpty()) {
            return urlPayloadList.getFirst();
        } else {
            return null;
        }
    }

    private boolean checkIfShortUrlExists(String shortenedUrl) {
        List<URLPayload> urlPayloadList = urlRepository.findByShortUrl(shortenedUrl);

        if (urlPayloadList != null && !urlPayloadList.isEmpty()) {
            return (urlPayloadList.getFirst() != null);
        } else {
            return false;
        }
    }
}
