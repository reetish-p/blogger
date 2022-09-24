package com.example.blogger.comments.dtos;

import lombok.Getter;

@Getter
public class CommentCreateRequestDTO {

    Comment comment;

    @Getter
    public class Comment {
        String title;
        String body;
    }
}