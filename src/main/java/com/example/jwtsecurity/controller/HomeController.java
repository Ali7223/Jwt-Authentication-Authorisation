package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.dao.AuthRequest;
import com.example.jwtsecurity.model.UserEntity;
import com.example.jwtsecurity.service.HomeServiceImpl;
import com.example.jwtsecurity.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    ProviderManager pM;
    @Autowired
    private HomeServiceImpl homeservice;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    @GetMapping("/user-secure")
    public String getUserSecure() {
        return "Secure User Returned";
    }

    @GetMapping("/user-insecure")
    public String getUserInsecure() {
        return "user insecure";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUser() {
        return "User_Role";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAdmin() {
        return "Admin_Role";
    }

    @GetMapping("/admin-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') and hasAuthority('ROLE_USER')")
    public String getAdminUser() {
        return "Admin_USER_Role";
    }

    @PostMapping("/create")
    public String create(@RequestBody UserEntity user) {
        String message = homeservice.saveUser(user);
        return message;


    }

    @PostMapping("/authenticate")
    public String authenticateToken(@RequestBody AuthRequest auth) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getName(), auth.getPassword()));


        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(auth.getName());
        }else {
            throw new UsernameNotFoundException("Invalid User !! ");
        }
    }


}
