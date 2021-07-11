package com.example.secureswagger.security;

import com.example.secureswagger.model.User;
import com.example.secureswagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
        User user = this.userService.findUser(s)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + s + "' not found"));

        // this creates a user detail object
        // populates it with username, Password and roles
        //org.springframework.security.core.userdetails.user.withUsername() builder
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("admin")   //ROLE
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}

