package com.example.blogger.articles.dtos;

import lombok.Getter;

@Getter
public class ArticleUpdateRequestDTO {
    private Article article;

    @Getter
    public class Article {
        private String title;
        private String description;
        private String body;
    }
}
