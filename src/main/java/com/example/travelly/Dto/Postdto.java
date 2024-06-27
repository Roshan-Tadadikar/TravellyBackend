package com.example.travelly.Dto;

import com.example.travelly.Model.Comments;
import com.example.travelly.Model.Image;
import com.example.travelly.Model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date addedTime;
    private Date updatedTime;
    private Set<Image> images;
    private Set<Comments> comments;
}
