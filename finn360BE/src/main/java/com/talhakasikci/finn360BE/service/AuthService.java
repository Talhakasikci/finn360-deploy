package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.auth.LoginRequest;
import com.talhakasikci.finn360BE.dto.auth.RegisterRequest;
import com.talhakasikci.finn360BE.dto.auth.AuthResponse;
import com.talhakasikci.finn360BE.model.Role;
import com.talhakasikci.finn360BE.model.User;
import com.talhakasikci.finn360BE.repository.UserRepository;
import com.talhakasikci.finn360BE.security.JwtUtils; // Yeni oluşturduğun sınıf
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Şifrelemek için
    private final AuthenticationManager authenticationManager; // Giriş kontrolü için
    private final JwtUtils jwtUtils; // Token üretmek için

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse register(RegisterRequest request) {
        // 1. Email kontrolü
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Bu email zaten kayıtlı!");
        }

        // 2. Yeni User oluşturma
        User user = new User();
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateJwtToken(authentication, savedUser.getId());



        // 3. Mesaj dönme
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .userUUID(user.getId())
                .message("Kayıt başarılı")
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        String jwtToken = jwtUtils.generateJwtToken(authentication, user.getId());



        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .userUUID(user.getId())
                .message("Giriş başarılı")
                .build();
    }
}