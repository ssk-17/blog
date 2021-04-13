package com.example.blog.service;

import com.example.blog.model.BlogPost;
import org.springframework.http.ResponseEntity;

public interface BlogService {
    ResponseEntity<String> upsert(String blogId, BlogPost blogPost);

    BlogPost getBlogPost(String blogId);
}
