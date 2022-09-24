package com.example.blogger.comments;

import com.example.blogger.comments.dtos.CommentDTO;
import com.example.blogger.comments.dtos.CommentResponseDTO;
import com.example.blogger.users.UserEntity;
import com.example.blogger.users.dtos.AuthorResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentObjectConverter {

    public CommentResponseDTO entityToResponse(CommentEntity commentEntity) {

        CommentDTO response = convert(commentEntity);
        return new CommentResponseDTO(response);
    }

    public List<CommentDTO> entityToResponse(List<CommentEntity> commentEntity) {
        return commentEntity.stream().map(CommentObjectConverter::convert).collect(Collectors.toList());
    }

    private static CommentDTO convert(CommentEntity commentEntity) {
        UserEntity author = commentEntity.getAuthor();
        return CommentDTO.builder()
                .id(commentEntity.getId())
                .title(commentEntity.getTitle())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .body(commentEntity.getBody())
                .author(AuthorResponseDTO.builder()
                        .bio(author.getBio())
                        .username(author.getUsername())
                        .image(author.getImage())
                        .following(false)
                        .build())
                .build();
    }

}