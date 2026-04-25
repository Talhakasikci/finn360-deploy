package com.talhakasikci.finn360BE.dto.article;

import com.talhakasikci.finn360BE.model.ArticleStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArticleDTO {
    // Common field for request and response
    private String id;
    private String title;
    private String body;
    private List<String> tags;

    private String authorId; // Input

    private String authorName; // Output
    private String authorEmail;

    private int votesCount;
    private ArticleStatus status;
    private LocalDateTime publishedAt;
}