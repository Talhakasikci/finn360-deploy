package com.talhakasikci.finn360BE.dto.user;

import com.talhakasikci.finn360BE.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
