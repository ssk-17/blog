package com.example.blog.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class BlogPost {
    private String author;
    private String title;
    private String content;
    private Date date;
}
