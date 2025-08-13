package org.systems.urlshortner;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.systems.urlshortner.controllers.URLController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UrlShortenerApplicationTests {

    @Autowired
    private URLController urlController;

    @Test
    void contextLoads() {
        assertThat(urlController).isNotNull();
    }

}
