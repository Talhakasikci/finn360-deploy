package com.talhakasikci.finn360BE.controller;

import com.talhakasikci.finn360BE.dto.user.UserResponseDTO;
import com.talhakasikci.finn360BE.model.User;
import com.talhakasikci.finn360BE.security.AuthenticatedUser;
import com.talhakasikci.finn360BE.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getMyProfile() {
        // Mevcut kimlik doğrulama bilgilerini al
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) principal;

            String userId = authenticatedUser.getUserId();
            User profile = userService.findUserById(userId);
            profile.setPassword(null);
            return ResponseEntity.ok(profile);
        }else {
            return ResponseEntity.status(403).build();
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String id){
        UserResponseDTO user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

}
