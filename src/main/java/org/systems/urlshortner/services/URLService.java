package org.systems.urlshortner.services;

import org.systems.urlshortner.model.URLPayload;

public interface URLService {
    URLPayload saveOriginalURL(URLPayload urlPayload) throws Exception;
    URLPayload getOriginalURL(String shortUrl) throws Exception;
}
