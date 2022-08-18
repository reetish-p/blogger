package com.example.blogger.users;

import com.example.blogger.common.BaseEntity;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
@Builder
@Getter
@Setter
@ToString
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
	private String avatar;
}