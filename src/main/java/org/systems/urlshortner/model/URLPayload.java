package org.systems.urlshortner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "url_mappings")
@Data
public class URLPayload {
    @Id
    private String id;

    private String originalUrl;
    private String shortenedUrl;

    private long clickCount;             // Total number of times the short link was used
    private LocalDateTime createdAt;     // When the short link was created
    private LocalDateTime lastAccessed;  // Last time someone clicked the link
    private LocalDateTime expiresAt;     // When the link expires (null = never expires)
}
