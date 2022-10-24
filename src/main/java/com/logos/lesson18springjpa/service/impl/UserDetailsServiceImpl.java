package com.logos.lesson18springjpa.service.impl;

import com.logos.lesson18springjpa.models.User;
import com.logos.lesson18springjpa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail.isPresent()){
            return byEmail.get();
        }
        throw new UsernameNotFoundException("User with email " + username + " not found");
    }
}
