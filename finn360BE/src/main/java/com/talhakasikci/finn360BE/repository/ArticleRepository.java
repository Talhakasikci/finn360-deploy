package com.talhakasikci.finn360BE.repository;

import com.talhakasikci.finn360BE.model.Article;
import com.talhakasikci.finn360BE.model.ArticleStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {
    List<Article> findAllByStatusOrderByPublishedAtDesc(ArticleStatus status);
}