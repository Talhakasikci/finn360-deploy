package com.talhakasikci.finn360BE.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection ="users")
public class User {

    @Id
    private String id;

   @Indexed(unique = true)//benzersiz email
    private String email;

    private String password;

    private String name;

    private String surname;

    private Role role;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(){
        this.id = java.util.UUID.randomUUID().toString();
    }

    public User(String email, String password, String name, String surname, Role role) {
        this.id = java.util.UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
}