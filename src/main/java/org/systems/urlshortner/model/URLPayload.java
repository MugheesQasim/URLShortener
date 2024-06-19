package org.systems.urlshortner.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection  = "url_mappings")
@Data
public class URLPayload {
    @Id
    private String id;
    private String originalUrl;
    private String shortenedUrl;
}
