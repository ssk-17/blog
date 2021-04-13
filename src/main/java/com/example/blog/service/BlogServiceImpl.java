package com.example.blog.service;

import com.example.blog.model.BlogPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BlogServiceImpl implements BlogService {
    Map<String, BlogPost> blogs = new HashMap<>();

    @Override
    public ResponseEntity<String> upsert(String blogId, BlogPost blogPost) {
        if (blogId == null) {
            if (isAnyFieldNull(blogPost))  // we would need all values for new blog
                return badRequest("Request Payload fields i.e any of author/title/content/date are missing!");

            String randomId = UUID.randomUUID().toString();
            blogs.put(randomId, blogPost);
            return ResponseEntity.status(HttpStatus.CREATED).body(randomId);
        } else {
            if (!blogs.containsKey(blogId))
                return badRequest("blog with id = " + blogId + " is not in the records");

            BlogPost bPost = blogs.get(blogId);
            if (blogPost.getAuthor() != null) bPost.setAuthor(blogPost.getAuthor());
            if (blogPost.getTitle() != null) bPost.setTitle(blogPost.getTitle());
            if (blogPost.getContent() != null) bPost.setContent(blogPost.getContent());
            if (blogPost.getDate() != null) bPost.setDate(blogPost.getDate());

            blogs.put(blogId, bPost); // update the existing blog post in our record.

            return ResponseEntity.ok("Blog Post updated for id=" + blogId + " with given blog post");
        }
    }

    @Override
    public BlogPost getBlogPost(String blogId) {
        return blogs.getOrDefault(blogId, null);
    }

    private ResponseEntity<String> badRequest(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    private boolean isAnyFieldNull(BlogPost blogPost) {
        return blogPost.getAuthor() == null || blogPost.getTitle() == null
                || blogPost.getContent() == null || blogPost.getDate() == null;
    }

}
