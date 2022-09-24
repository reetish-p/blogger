package com.example.blogger.articles;


import com.example.blogger.articles.dtos.ArticleCreateRequestDTO;
import com.example.blogger.articles.dtos.ArticleResponseDTO;
import com.example.blogger.articles.dtos.ArticleUpdateRequestDTO;
import com.example.blogger.comments.CommentEntity;
import com.example.blogger.comments.CommentObjectConverter;
import com.example.blogger.comments.dtos.CommentCreateRequestDTO;
import com.example.blogger.comments.dtos.CommentDTO;
import com.example.blogger.comments.dtos.CommentResponseDTO;
import com.example.blogger.common.ErrorDTO;
import com.example.blogger.common.GenericResponseDTO;
import com.example.blogger.users.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    private final ArticleService articleService;
    ArticleObjectConverter articleObjectConverter;
    CommentObjectConverter commentObjectConverter;

    public ArticlesController(
            ArticleService articleService,
            ArticleObjectConverter articleObjectConverter,
            CommentObjectConverter commentObjectConverter) {
        this.articleService = articleService;
        this.articleObjectConverter = articleObjectConverter;
        this.commentObjectConverter = commentObjectConverter;
    }

    @GetMapping(value = "/feed", produces = "application/json")
    ResponseEntity<List<ArticleResponseDTO>> feedArticles(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        var article = articleService.getArticleFeed(userEntity, limit, offset);
        List<ArticleResponseDTO> articleResponseList = article.stream()
                .map(x -> articleObjectConverter.entityToResponse(x))
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleResponseList);
    }

    @GetMapping(value = "/{slug}", produces = "application/json")
    ResponseEntity<ArticleResponseDTO> getArticleBySlug(@PathVariable(value = "slug") String slug) {
        var article = articleService.getArticleBySlug(slug);
        return ResponseEntity.ok(articleObjectConverter.entityToResponse(article));
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    ResponseEntity<ArticleResponseDTO> createArticle(
            @RequestBody ArticleCreateRequestDTO body,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        var articleEntity = articleService.createArticle(
                body.getArticle().getTitle(),
                body.getArticle().getBody(),
                body.getArticle().getDescription(),
                body.getArticle().getTags(),
                userEntity
        );
        return ResponseEntity.ok(articleObjectConverter.entityToResponse(articleEntity));
    }

    @PutMapping(value = "/{slug}", produces = "application/json")
    ResponseEntity<ArticleResponseDTO> updateArticle(
            @PathVariable(value = "slug", required = true) String slug,
            @RequestBody ArticleUpdateRequestDTO body,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        ArticleEntity article = articleService.updateArticle(body.getArticle(), slug, userEntity);
        return ResponseEntity.ok(articleObjectConverter.entityToResponse(article));
    }

    @DeleteMapping(value = "/{slug}", produces = "application/json")
    ResponseEntity<GenericResponseDTO> deleteArticle(
            @PathVariable(value = "slug", required = true) String slug,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        articleService.deleteArticleBySlug(slug, userEntity);
        return ResponseEntity.ok(new GenericResponseDTO("Article Deleted"));
    }

    @PostMapping(value = "/{slug}/comments", produces = "application/json")
    ResponseEntity<CommentResponseDTO> addCommentToArticle(
            @PathVariable(value = "slug", required = true) String slug,
            @RequestBody CommentCreateRequestDTO body,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        CommentEntity response = articleService.addCommentToArticle(slug, body.getComment(), userEntity);
        return ResponseEntity.ok(commentObjectConverter.entityToResponse(response));
    }

    @GetMapping(value = "/{slug}/comments", produces = "application/json")
    ResponseEntity<List<CommentDTO>> getCommentsFromArticle(
            @PathVariable(value = "slug", required = true) String slug
    ) {
        List<CommentEntity> comments = articleService.getAllCommentBySlug(slug);
        return ResponseEntity.ok(commentObjectConverter.entityToResponse(comments));
    }

    @DeleteMapping(value = "/{slug}/comments/{id}", produces = "application/json")
    ResponseEntity<GenericResponseDTO> deleteComment(
            @PathVariable(value = "slug", required = true) String slug,
            @PathVariable(value = "id", required = true) int id,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        articleService.deleteComment(id, userEntity);
        return ResponseEntity.ok(new GenericResponseDTO("Comment deleted successfully."));
    }

    @PostMapping(value = "/{slug}/favorite", produces = "application/json")
    ResponseEntity<ArticleResponseDTO> favoriteArticle(
            @PathVariable(value = "slug", required = true) String slug,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        ArticleEntity articleEntity = articleService.favoriteArticle(slug, userEntity);
        return ResponseEntity.ok(articleObjectConverter.entityToResponse(articleEntity));
    }

    @DeleteMapping(value = "/{slug}/favorite", produces = "application/json")
    ResponseEntity<ArticleResponseDTO> unfavoriteArticle(
            @PathVariable(value = "slug", required = true) String slug,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        ArticleEntity articleEntity = articleService.unfavoriteArticle(slug, userEntity);
        return ResponseEntity.ok(articleObjectConverter.entityToResponse(articleEntity));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(RuntimeException ex, WebRequest request) {
        ErrorDTO exceptionResponse = new ErrorDTO(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
