package com.talhakasikci.finn360BE.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "articles")
public class Article {

    @Id
    private String id;

    private String title;

    private String body;

    private List<String> tags;

    private ArticleStatus status;

    private int votesCount = 0;

    @DBRef
    private User author;

    @CreatedDate
    private LocalDateTime publishedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}