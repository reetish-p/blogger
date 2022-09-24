package com.example.blogger.articles.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleResponseDTO {
    private ArticleDTO article;
}