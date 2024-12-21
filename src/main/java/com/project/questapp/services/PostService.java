package com.project.questapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.request.PostCreateRequest;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional
    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        } else {
            return postRepository.findAll();
        }
    }

    @Transactional
    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Transactional
    public Post createOnePost(PostCreateRequest newPostRequest) {
        if (newPostRequest.getUserId() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        User user = userService.getOneUser(newPostRequest.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Post toSave = new Post();
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        toSave.setVersion(0L);  // Version alanını başlat
        return postRepository.save(toSave);
    }

    @Transactional
    public Post updatePost(Long postId, PostCreateRequest updatePostRequest) {
        if (postId == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        Post post = entityManager.find(Post.class, postId, LockModeType.PESSIMISTIC_WRITE);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        post.setText(updatePostRequest.getText());
        post.setTitle(updatePostRequest.getTitle());
        return postRepository.save(post);
    }
}
