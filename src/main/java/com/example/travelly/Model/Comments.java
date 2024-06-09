package com.example.travelly.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private Date addedDate;
    private Date updatedDate;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
