package com.project.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.questapp.entities.Post;
import com.project.questapp.services.PostService;
import com.project.questapp.request.PostCreateRequest;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
        return postService.createOnePost(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostCreateRequest updatePostRequest) {
        if (postId == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        return postService.updatePost(postId, updatePostRequest);
    }
}


