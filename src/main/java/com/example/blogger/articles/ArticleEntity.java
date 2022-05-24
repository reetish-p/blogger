package com.example.blogger.articles;

import java.util.List;

import com.example.blogger.common.BaseEntity;
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
@Entity
public class ArticleEntity extends BaseEntity {

	@Column(nullable = false)
	@NonNull
	private String heading;

	@Column(nullable = false)
	@NonNull
	private String subheading;

	@Column(nullable = false)
	@NonNull
	private String slug;

	@Column(nullable = false)
	@NonNull
	private String content;

	@ManyToOne
	@JoinColumn(name = "author_id" )
	private UserEntity author;
	//private List<String> tags;
}