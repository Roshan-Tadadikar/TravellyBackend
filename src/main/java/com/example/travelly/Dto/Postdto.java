package com.example.travelly.Dto;

import com.example.travelly.Model.Image;
import com.example.travelly.Model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comments;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Postdto {

    private Integer id;
    private String content;
    private Integer isBookmark;
    private LocalDateTime addedTime;
    private LocalDateTime updatedTime;
    private Set<Imagedto> images;
    private Set<Comments> comments;
}
