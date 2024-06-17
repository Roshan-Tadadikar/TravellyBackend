package com.example.travelly.Dto;

import com.example.travelly.Model.Comments;
import com.example.travelly.Model.Image;
import com.example.travelly.Model.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

public class Postdto {

    private Integer id;
    private String content;
    private Integer isBookmark;
    private Date addedTime;
    private Date updatedTime;
    private User user;
    private Set<Image> images;
    private Set<Comments> comments;
}
