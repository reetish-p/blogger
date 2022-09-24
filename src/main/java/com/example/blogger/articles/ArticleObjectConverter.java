package com.example.blogger.articles;

import com.example.blogger.articles.dtos.ArticleDTO;
import com.example.blogger.articles.dtos.ArticleResponseDTO;
import com.example.blogger.users.UserEntity;
import com.example.blogger.users.dtos.AuthorResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleObjectConverter {
    public ArticleResponseDTO entityToResponse(ArticleEntity articleEntity){
        List<String> tagNames = new ArrayList<>();
        //TODO : add fav count and favorited
        articleEntity.getTags().stream().forEach(tag ->
            tagNames.add(tag.getName())
        );

        UserEntity author = articleEntity.getAuthor();
        var authorResponse = AuthorResponseDTO.builder()
                .bio(author.getBio())
                .username(author.getUsername())
                .image(author.getImage())
                .following(false)
                .build();
        return new ArticleResponseDTO(ArticleDTO.builder().slug(articleEntity.getSlug())
                .title(articleEntity.getTitle())
                .body(articleEntity.getBody())
                .description(articleEntity.getDescription())
                .tagList(tagNames)
                .createdAt(articleEntity.getCreatedAt())
                .updatedAt(articleEntity.getUpdatedAt())
                .favoritesCount(0)
                .favorited(false)
                .author(authorResponse)
                .build());
    }
}
