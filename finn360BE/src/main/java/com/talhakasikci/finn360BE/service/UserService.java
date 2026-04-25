package com.talhakasikci.finn360BE.service;

import com.talhakasikci.finn360BE.dto.user.UserResponseDTO;
import com.talhakasikci.finn360BE.model.User;
import com.talhakasikci.finn360BE.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUserById(String uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı (uuid)."));
    }

    public List<UserResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .role(user.getRole())
                        .build())
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .role(user.getRole())
                .build();
    }
}