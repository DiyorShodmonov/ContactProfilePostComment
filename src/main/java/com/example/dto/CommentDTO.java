package com.example.dto;

import com.example.entity.PostEntity;
import com.example.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    private String content;
    private Integer postId;
    private Integer profileId;
    private LocalDateTime created_date;

    private PostDTO post;
    private ProfileDTO profile;
}
