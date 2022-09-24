package com.example.blogger.comments.dtos;

import com.example.blogger.users.dtos.AuthorResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CommentDTO {
    private Integer id;
    private String title;
    private Date createdAt;
    private Date updatedAt;
    private String body;
    private AuthorResponseDTO author;
}
