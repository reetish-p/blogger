package com.example.blogger.articles.dtos;

import com.example.blogger.users.dtos.AuthorResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class ArticleDTO {
        private String slug;
        private String title;
        private String description;
        private String body;
        private List<String> tagList;
        private Date createdAt;
        private Date updatedAt;
        private boolean favorited;
        private int favoritesCount;
        private AuthorResponseDTO author;
}
