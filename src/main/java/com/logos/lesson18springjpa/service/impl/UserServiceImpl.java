package com.logos.lesson18springjpa.service.impl;

import com.logos.lesson18springjpa.models.User;
import com.logos.lesson18springjpa.repository.UserRepository;
import com.logos.lesson18springjpa.service.UserService;
import com.logos.lesson18springjpa.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(int id) {
        Optional<User> user = userRepository.searchById(id);

        if(user.isPresent()){
            return user.get();
        }
        throw new RuntimeException("UserNoTFound");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()){
            throw new RuntimeException("User is register now");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userRepository.save(user) ;
    }

    @Override
    public void deleteUser(int id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String transferFile(MultipartFile file) {
        String pathToFolder = FileUtils.getImagesfolder();
        System.out.println("PATH" + pathToFolder);
        try {
            file.transferTo(new File(pathToFolder + file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getOriginalFilename();

    }
}
