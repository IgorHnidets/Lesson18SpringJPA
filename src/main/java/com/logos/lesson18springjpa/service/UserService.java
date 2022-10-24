package com.logos.lesson18springjpa.service;

import com.logos.lesson18springjpa.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserById(int id);

    List<User> getAllUsers();

    User saveUser(User user);

    void deleteUser(int id);

    User updateUser(User user);

    String transferFile(MultipartFile file);

}