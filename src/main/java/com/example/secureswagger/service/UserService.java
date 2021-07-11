package com.example.secureswagger.service;

import com.example.secureswagger.model.Token;
import com.example.secureswagger.model.User;
import com.example.secureswagger.repository.UserRepository;
import com.example.secureswagger.security.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtConfiguration jwtConfiguration;

    public Optional<User> findUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    public Token findAuthorizedUser(String username, String password) {
        User user = this.userRepository
                .findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        //In real application, check password in hash format
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Password is incorrect");
        }

        Token response = new Token(username, this.createToken(username, "admin"));//in response we don't need password
        return response;
    }

    private String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", new SimpleGrantedAuthority(role));

        Date now = new Date();
        LocalDateTime dateTime = LocalDateTime.now()
                .plus(Duration.of(this.jwtConfiguration.getExpiration(), ChronoUnit.MINUTES));
        Date validity = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, this.jwtConfiguration.getSecretKey())
                .compact();
    }
}
