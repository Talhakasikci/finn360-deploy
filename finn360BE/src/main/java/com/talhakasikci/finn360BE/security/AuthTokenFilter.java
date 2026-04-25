package com.talhakasikci.finn360BE.security;

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

@Component // Bu anotasyon sayesinde Spring bunu otomatik tanır
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. İstekten JWT'yi ayıkla
            String jwt = parseJwt(request);

            // 2. Token var mı ve geçerli mi kontrol et
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                // 3. Token içinden Email'i (Username) al
                String email = jwtUtils.getUserNameFromJwtToken(jwt);

                String uuid = jwtUtils.getUserUUIDFromJwtToken(jwt);

                // 4. Veritabanından kullanıcı detaylarını yükle
                AuthenticatedUser authenticatedUser = new AuthenticatedUser(uuid, email);


                // 5. Kimlik doğrulama nesnesini oluştur
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Sistemi "Bu kişi giriş yaptı" olarak güncelle
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Kullanıcı kimlik doğrulaması yapılamadı: {}", e);
        }

        // Zincirdeki diğer filtrelere devam et
        filterChain.doFilter(request, response);
    }

    // Header'dan "Bearer " kısmını temizleyip sadece token'ı alan yardımcı metod
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}