package com.example.jwtsecurity.filter;

import com.example.jwtsecurity.config.UserEntityUserDetailsService;
import com.example.jwtsecurity.model.UserEntity;
import com.example.jwtsecurity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    @Autowired()
    private UserEntityUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader("Authorization");
        String token=null;
        String username=null;
        if(authenticationHeader != null && authenticationHeader.startsWith("Bearer ")){
            token = authenticationHeader.substring(7);
            username = jwtService.extractUsername(token);
            System.out.println(username);
            System.out.println("this line in filter class");
        }
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println(userDetails.getUsername());
            System.out.println("this is line 2");

            if(jwtService.validateToken(token, userDetails)){
                System.out.println("this is line 3");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request,response);

    }
}
