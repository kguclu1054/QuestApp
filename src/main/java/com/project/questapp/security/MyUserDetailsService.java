package com.project.questapp.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return new User("user", "$2a$10$DowQwB/O6qYTOzzvQ5QHgu6GONxOTF7vDpxYJu8K6TLpiLZ3QPOqK", new ArrayList<>()); // Şifre: password (BCrypt kodlaması)
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}

