package com.example.travelly.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Data
public class Userdto {
    private Integer id;
    @NotEmpty
    private String name;
    @Email
    private String email;
    private Imagedto image;
    private Date createdTime;
    private Date updatedTime;
}
