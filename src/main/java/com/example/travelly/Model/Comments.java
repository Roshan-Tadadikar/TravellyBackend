package com.example.travelly.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private LocalDateTime addedDate;
    private LocalDateTime updatedDate;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
