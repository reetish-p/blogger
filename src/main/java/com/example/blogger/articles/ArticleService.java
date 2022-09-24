package com.example.blogger.articles;

import com.example.blogger.articles.dtos.ArticleUpdateRequestDTO;
import com.example.blogger.comments.CommentEntity;
import com.example.blogger.comments.CommentRepository;
import com.example.blogger.comments.dtos.CommentCreateRequestDTO;
import com.example.blogger.tags.TagService;
import com.example.blogger.users.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

import static java.util.Objects.isNull;

@Service
public class ArticleService {
    private final ArticleRepository articlesRepository;
    private final CommentRepository commentRepository;
    private final TagService tagsService;

    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException() {
            super("Access Denied");
        }
    }

    public ArticleService(ArticleRepository articlesRepository, CommentRepository commentRepository, TagService tagsService) {
        this.articlesRepository = articlesRepository;
        this.commentRepository = commentRepository;
        this.tagsService = tagsService;
    }

    public List<ArticleEntity> getAllArticles(int limit,int offset){
        PageRequest pr = PageRequest.of(offset/limit,limit);
        return articlesRepository.findAll(pr).getContent();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        return articlesRepository.findArticleEntityBySlug(slug);
    }

    public String getSlug(String title) {
        return title.toLowerCase(Locale.ROOT).replace(' ', '-');
    }

    public ArticleEntity createArticle(String articleTitle, String articleDescription, String articleBody, List<String> articleTagsList, UserEntity currentLoggedInUser){
        var articleTagEntities = tagsService.createTags(articleTagsList);
        var articleSlug = this.getSlug(articleTitle);
        var articleEntity = ArticleEntity.builder()
                .slug(articleSlug)
                .title(articleTitle)
                .description(articleDescription)
                .body(articleBody)
                .tags(articleTagEntities)
                .author(currentLoggedInUser)
                .build();
        return articlesRepository.save(articleEntity);
    }

    public List<ArticleEntity> getArticleFeed(UserEntity userEntity,int limit,int offset){
        PageRequest pr = PageRequest.of(offset/limit,limit);
        return articlesRepository.findArticleFeed(userEntity.getId(),pr);
    }

    public ArticleEntity updateArticle(ArticleUpdateRequestDTO.Article articleDTO, String slug, UserEntity user){
        ArticleEntity oldArticle = articlesRepository.findArticleEntityBySlug(slug);
        if(oldArticle.getAuthor().getId() != user.getId())
            throw new AccessDeniedException();
        if(!isNull(articleDTO.getBody())) oldArticle.setBody(articleDTO.getBody());
        if(!isNull(articleDTO.getDescription())) oldArticle.setDescription(articleDTO.getDescription());
        if(!isNull(articleDTO.getTitle())){
            oldArticle.setTitle(articleDTO.getTitle());
            oldArticle.setSlug(this.getSlug(articleDTO.getTitle()));
        }
        return articlesRepository.save(oldArticle);
    }

    @Transactional
    public void deleteArticleBySlug(String slug, UserEntity userEntity) {
        ArticleEntity article = getArticleBySlug(slug);
        if (article.getAuthor().getId() != userEntity.getId())
            throw new AccessDeniedException();
        articlesRepository.deleteTagMappingByArticleId(article.getId());
        articlesRepository.deleteBySlug(slug);
    }

    public ArticleEntity favoriteArticle(String slug, UserEntity userEntity) {
        ArticleEntity articleEntity = getArticleBySlug(slug);
        articleEntity.getFans().add(userEntity);
        return articlesRepository.save(articleEntity);
    }

    public ArticleEntity unfavoriteArticle(String slug, UserEntity userEntity) {
        ArticleEntity articleEntity = getArticleBySlug(slug);
        articlesRepository.unfavoriteArticle(userEntity.getId(), articleEntity.getId());
        return articleEntity;
    }

    public CommentEntity addCommentToArticle(String slug, CommentCreateRequestDTO.Comment body, UserEntity currentLoggedInUser) {
        ArticleEntity articleEntity = getArticleBySlug(slug);
        CommentEntity commentEntity = CommentEntity.builder()
                .title(body.getTitle())
                .body(body.getBody())
                .author(currentLoggedInUser)
                .article(articleEntity)
                .build();
        return commentRepository.save(commentEntity);
    }

    public List<CommentEntity> getAllCommentBySlug(String slug) {
        ArticleEntity articleEntity = getArticleBySlug(slug);
        return commentRepository.findCommentEntityByArticle(articleEntity);
    }
    public void deleteComment(Integer id, UserEntity userEntity) {
        CommentEntity comment = commentRepository.getOne(id);
        if (userEntity.getId() != comment.getAuthor().getId())
            throw new AccessDeniedException();
        commentRepository.deleteById(id);
    }

}
