package com.example.blogger.articles;

import com.example.blogger.articles.dtos.ArticleUpdateRequestDTO;
import com.example.blogger.comments.CommentEntity;
import com.example.blogger.comments.dtos.CommentCreateRequestDTO;
import com.example.blogger.users.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ArticleService {
    public List<ArticleEntity> getAllArticles(int limit,int offset);
    public ArticleEntity getArticleBySlug(String slug);
    public String getSlug(String title);
    public ArticleEntity createArticle(String articleTitle, String articleDescription, String articleBody, List<String> articleTagsList, UserEntity currentLoggedInUser);
    public List<ArticleEntity> getArticleFeed(UserEntity userEntity,int limit,int offset);
    public ArticleEntity updateArticle(ArticleUpdateRequestDTO.Article articleDTO, String slug, UserEntity user);
    public void deleteArticleBySlug(String slug, UserEntity userEntity);
    public ArticleEntity favoriteArticle(String slug, UserEntity userEntity);
    public ArticleEntity unfavoriteArticle(String slug, UserEntity userEntity);
    public CommentEntity addCommentToArticle(String slug, CommentCreateRequestDTO.Comment body, UserEntity currentLoggedInUser);
    public List<CommentEntity> getAllCommentBySlug(String slug);
    public void deleteComment(Integer id, UserEntity userEntity);

}
