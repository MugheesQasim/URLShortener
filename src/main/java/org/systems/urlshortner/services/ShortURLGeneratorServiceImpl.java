package org.systems.urlshortner.services;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ShortURLGeneratorServiceImpl implements ShortURLGeneratorService {
    private final static String URL_PREFIX = "localhost/";
    private final static long INITIAL_VALUE = 1000_000_000;
    private final static long UPPER_BOUND = 1100_000_000;
    private static final char[] ALPHABETS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private final Random random = new Random();

    public String generate() {
        long randomCount = getRandomValue();
        return base58Encoder(randomCount);
    }

    private long getRandomValue() {
        return INITIAL_VALUE + random.nextLong(UPPER_BOUND);
    }

    private String base58Encoder(long input) {
        StringBuilder generatedUrlSb = new StringBuilder();
        while (input != 0) {
            generatedUrlSb.append(ALPHABETS[(int) input % 58]);
            input /= 58;
        }
        return URL_PREFIX + generatedUrlSb;
    }

}
