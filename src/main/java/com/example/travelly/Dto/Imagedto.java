package com.example.travelly.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

import java.util.Date;

@Data
public class Imagedto {
    private Integer id;
    private String name;
    private byte[] content;
    private Date addedTime;
    private Date updatedTime;
}
