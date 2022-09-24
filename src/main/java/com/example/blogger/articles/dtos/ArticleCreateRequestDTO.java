package com.example.blogger.articles.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleCreateRequestDTO {
    private Article article;

    @Getter
    public class Article {
        private String title;
        private String description;
        private String body;
        @JsonProperty("tagList")
        private List<String> tags;
    }
}
