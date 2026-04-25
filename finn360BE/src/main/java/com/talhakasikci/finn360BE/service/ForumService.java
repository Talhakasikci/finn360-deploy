package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.article.ArticleDTO; // DÜZELTİLDİ: dto.article
import com.talhakasikci.finn360BE.model.Article;
import com.talhakasikci.finn360BE.model.ArticleStatus;
import com.talhakasikci.finn360BE.model.Role;
import com.talhakasikci.finn360BE.model.User;
import com.talhakasikci.finn360BE.repository.ArticleRepository;
import com.talhakasikci.finn360BE.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ForumService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    // Creating a new article
    public ArticleDTO createArticle(ArticleDTO dto) {
        // Check if authorId is provided
        if (dto.getAuthorId() == null) {
            throw new RuntimeException("Yazar ID'si gereklidir.");
        }

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Yazar bulunamadı."));

        // Authorization check
        if (author.getRole() != Role.VERIFIED) {
            throw new RuntimeException("Yalnızca onaylı kullanıcılar makale paylaşabilir.");
        }

        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setBody(dto.getBody());
        article.setTags(dto.getTags());
        article.setAuthor(author);
        article.setStatus(ArticleStatus.PUBLISHED);
        article.setPublishedAt(LocalDateTime.now());

        articleRepository.save(article);

        return mapToDTO(article);
    }

    public List<ArticleDTO> getFeed() {
        List<Article> articles = articleRepository.findAllByStatusOrderByPublishedAtDesc(ArticleStatus.PUBLISHED);
        return articles.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Entity
    private ArticleDTO mapToDTO(Article article) {
        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .tags(article.getTags())
                .authorName(article.getAuthor().getName())
                .authorEmail(article.getAuthor().getEmail())
                .votesCount(article.getVotesCount())
                .status(article.getStatus())
                .publishedAt(article.getPublishedAt())
                .build();
    }
}