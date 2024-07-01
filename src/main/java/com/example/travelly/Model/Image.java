package com.example.travelly.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Images")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;
    private LocalDateTime addedTime;
    private LocalDateTime updatedTime;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Posts posts;
}
