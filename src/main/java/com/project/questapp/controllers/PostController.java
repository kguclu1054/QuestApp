package com.project.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.entities.Post;
import com.project.questapp.request.PostCreateRequest;
import com.project.questapp.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping
	public List<Post> getAllPost(@RequestParam Optional<Long> userId){
		return postService.getallPost(userId);
	}
	
	@PostMapping
	public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
		return postService.createOnePost(newPostRequest);
	}
	
	@GetMapping("/{postId}")
	public Post getOnePost(@PathVariable Long postId) {
		return postService.getOnePostById(postId);
	}
}
