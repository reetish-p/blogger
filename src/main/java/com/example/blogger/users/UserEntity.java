package com.example.blogger.users;

import com.example.blogger.articles.ArticleEntity;
import com.example.blogger.common.BaseEntity;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity extends BaseEntity {

	@Column(nullable = false,unique = true)
	@NonNull
	private String email;

	@Column(nullable = false, unique = true)
	@NonNull
	private String username;

	@Column(nullable = false)
	@NonNull
	private String password;

	private String bio;
	private String image;

	@ManyToMany(mappedBy = "fans", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ArticleEntity> favorite;

	@ManyToMany
	@JoinTable(name = "user_followers")
	private Set<UserEntity> followers;

}