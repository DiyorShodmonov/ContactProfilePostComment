package com.example.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostIdProfileIdPostCreatedDate {
    private Integer postId;
    private Integer profileId;
    private LocalDateTime created_date;
}
