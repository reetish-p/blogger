package com.example.blogger.users.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthorResponseDTO {
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
