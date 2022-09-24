package com.example.blogger.comments.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommentResponseDTO {
    CommentDTO comment;
}