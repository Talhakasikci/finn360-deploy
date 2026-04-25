package com.talhakasikci.finn360BE.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class AuthenticatedUser implements UserDetails {

    private final String userId; // UUID'yi burada tutacağız!
    private final String username; // Email'i de tutabiliriz ama asıl amacımız userId

    // Constructor'ı sadece gerekli alanlarla oluşturabiliriz
    public AuthenticatedUser(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    // Spring Security'nin gerektirdiği metotlar
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override
    public String getPassword() { return null; } // Şifreye ihtiyacımız yok, token doğrulandı.
    @Override
    public String getUsername() { return username; }

    // Hesap durum metotları (Token doğrulandıysa hepsi true olmalı)
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}