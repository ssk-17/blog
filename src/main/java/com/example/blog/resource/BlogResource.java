package com.example.blog.resource;

import com.example.blog.model.BlogPost;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
public class BlogResource {

    private BlogService blogService;

    @Autowired
    public BlogResource(BlogService blogService) {
        this.blogService = blogService;
    }

    /*
       Response status:
           201: created new blog post
           200: updated existing valid blog post
           400: bad request
    */
    @PostMapping("/blog")
    public ResponseEntity<String> upsert(@RequestParam(value = "id", required = false) String blogId,
                                         @Valid @RequestBody @NotNull BlogPost blogPost) {
        return blogService.upsert(blogId, blogPost);
    }

    /*
       Response status:
           200: get valid blog post with given blogId
           400: bad request
    */
    @GetMapping("/blog/{id}")
    public ResponseEntity getBlog(@PathVariable("id") @NotBlank String blogId) {

        BlogPost blogPost = blogService.getBlogPost(blogId);
        return blogPost == null ? ResponseEntity.badRequest()
                .body("blog with id = " + blogId + " is not in the records") :
                ResponseEntity.ok(blogPost);
    }
}
