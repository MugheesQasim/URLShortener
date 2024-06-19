package org.systems.urlshortner.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.systems.urlshortner.model.URLPayload;

import java.util.List;

@Repository
public interface URLRepository extends MongoRepository<URLPayload, String> {
    @Query(value = "{'shortenedUrl' : ?0}")
    List<URLPayload> findByShortUrl(String shortUrl);
}
