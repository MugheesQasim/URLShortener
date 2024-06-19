package org.systems.urlshortner.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.systems.urlshortner.model.URLPayload;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class URLRepositoryTest {
    @Autowired
    private URLRepository urlRepository;

    @Test
    public void testFindByShortUrl() {
        URLPayload urlPayload = new URLPayload();
        urlPayload.setOriginalUrl("https://example.com/very-long-url-that-needs-shortening");
        urlPayload.setShortenedUrl("chotaUrl.com/YDyYK3");

        List<URLPayload> urlPayloadFoundList = urlRepository.findByShortUrl(urlPayload.getShortenedUrl());
        assertThat(urlPayloadFoundList.isEmpty()).isNotEqualTo(false);

        if (!urlPayloadFoundList.isEmpty()) {
            assertThat(urlPayloadFoundList.getFirst() != null).isEqualTo(true);
        }
    }
}