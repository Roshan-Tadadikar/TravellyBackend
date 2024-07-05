package com.example.travelly.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private Integer isBookmark;
    private LocalDateTime addedTime;
    private LocalDateTime updatedTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    // mapped by means that it is the one which doesnt have the foreign key
    @OneToMany(mappedBy = "post")
    private Set<Comments> comments;
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    @OneToMany(mappedBy = "posts")
    private Set<Likes> likes;
}
