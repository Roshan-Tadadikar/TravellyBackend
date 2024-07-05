package com.example.travelly.Dto;

import com.example.travelly.Model.Posts;
import com.example.travelly.Model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private Integer id;
    private String content;
    private LocalDateTime addedDate;
    private LocalDateTime updatedDate;
    private Userdto user;
}
