package com.example.blogger.articles;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    List<ArticleEntity> findByTitleContainsIgnoreCase(String title);
    ArticleEntity findArticleEntityBySlug(String slug);

    @Query(value = "select * from articles a where a.author_id in (select users_id from user_followers uf where" +
            " followers_id = ?1 ) order by created_at desc",
            nativeQuery = true)
    List<ArticleEntity> findArticleFeed(Integer userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "delete from articles a where a.slug = ?1", nativeQuery = true)
    void deleteBySlug(String slug);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM favourites WHERE fans_id = ?1 AND favorite_id = ?2 ", nativeQuery = true)
    void unfavoriteArticle(Integer userId, Integer articleId);

    @Modifying
    @Transactional
    @Query(value = "delete from articles_tags a where a.articles_id = ?1", nativeQuery = true)
    void deleteTagMappingByArticleId(Integer articleId);
}
