package com.ai.resume.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ai.resume.entity.User;
import com.ai.resume.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);

        if (userOpt.isPresent() &&
            passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt.get();
        }
        return null;
    }
}


