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
@Entity
public class UserEntity extends BaseEntity {

	private String bio;
	private String avatar;

	@Column(nullable = false,unique = true)
	@NonNull
	private String email;

	@Column(nullable = false)
	@NonNull
	private String password;

	@Column(nullable = false, unique = true)
	@NonNull
	private String username;
}