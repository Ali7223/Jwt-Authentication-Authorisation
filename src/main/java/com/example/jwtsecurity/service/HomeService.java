package com.example.jwtsecurity.service;

import com.example.jwtsecurity.dao.AuthRequest;
import com.example.jwtsecurity.model.UserEntity;

public interface HomeService {

    public String saveUser(UserEntity user);
}
