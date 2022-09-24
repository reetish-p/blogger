package com.example.blogger.articles;

import java.util.List;
import java.util.Set;

import com.example.blogger.comments.CommentEntity;
import com.example.blogger.common.BaseEntity;
import com.example.blogger.tags.TagEntity;
import com.example.blogger.users.UserEntity;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "articles")
public class ArticleEntity extends BaseEntity {
	@Column(nullable = false)
	@NonNull
	private String slug;

	@Column(nullable = false)
	@NonNull
	private String title;

	@Column(nullable = false)
	@NonNull
	private String description;

	@Column(nullable = false)
	@NonNull
	private String body;

	@ManyToOne(fetch = FetchType.EAGER)
	private UserEntity author;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private List<CommentEntity> comments;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<TagEntity> tags;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "favourites")
	private Set<UserEntity> fans;

}