package com.example.jwtsecurity.service;

import com.example.jwtsecurity.model.UserEntity;
import com.example.jwtsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public String saveUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "USER ADDED SUCCESSFULLY";

    }
}
