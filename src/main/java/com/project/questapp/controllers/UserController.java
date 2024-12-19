package com.project.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.entities.User;
import com.project.questapp.repos.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/users")
public class UserController {

    @PersistenceContext 
    private EntityManager entityManager; // EntityManager eklendi
    
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @PostMapping
    @Transactional
    public User createUser(@RequestBody User newUser) {
        if (newUser.getId() == null) {
            entityManager.persist(newUser); // Yeni kullanıcılar için ID null olacak, persist kullan
        } else {
            newUser = entityManager.merge(newUser); // Varlık zaten mevcutsa merge kullan
        }
        return newUser;
    }
    
    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUser_name(newUser.getUser_name());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        } else {
            return null;
        }
    }
    
    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
    }
}
