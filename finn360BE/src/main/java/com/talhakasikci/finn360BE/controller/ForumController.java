package com.talhakasikci.finn360BE.controller;

import com.talhakasikci.finn360BE.dto.article.ArticleDTO;
import com.talhakasikci.finn360BE.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/feed")
    public ResponseEntity<List<ArticleDTO>> getFeed() {
        return ResponseEntity.ok(forumService.getFeed());
    }

    @PostMapping("/create")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO request) {
        return ResponseEntity.ok(forumService.createArticle(request));
    }
}