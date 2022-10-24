package com.logos.lesson18springjpa.controllers;


import com.logos.lesson18springjpa.models.User;
import com.logos.lesson18springjpa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@Controller("/user") // очікує що повернеться якась сторінка а не стрічка
@Controller("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public String getAllUsers(Model model){
        List<User> all = userService.getAllUsers();
        model.addAttribute("users",all);
        return "users";
    }


    @Operation(summary = "Get user by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Have not acces")
    })
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id,Model model){
        try {
            User userById = userService.getUserById(id);
            model.addAttribute("user", userById);
        } catch (RuntimeException e){
            e.getMessage();
            return "404";
        }
        return "user-info";
    }

    @PostMapping("/user/update")
    public String updateusers(){
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
            if (user.getAvatar().isEmpty()){
                user.setAvatar("defaultimage.png");
                userService.updateUser(user);

            }
        }
        return "users";
    }

    @PostMapping("/user/save")
    public String saveUser(@RequestParam String firstname,
                           @RequestParam String lastname,
                           @RequestParam String email,
                           @RequestParam MultipartFile avatar,
                           @RequestParam String password, Model model){
        User user = new User(firstname,lastname,email,password);
        if (avatar.isEmpty()){
            user.setAvatar("defaultimage.png");
            userService.saveUser(user);
            model.addAttribute("user",user);
            return "user-info";
        }
        String file = userService.transferFile(avatar);
        user.setAvatar(file);
        User save = userService.saveUser(user);
        model.addAttribute("user",user);
        return "user-info";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getById(@PathVariable int id) {
//        try {
//            return ResponseEntity.ok().body(userService.getUserById(id));
//        }catch (RuntimeException e){
//            return ResponseEntity.status(404).build();
//        }
//    }
//
//    @PostMapping
//    public User save(@RequestBody User user){
//        System.out.println(user);
//        return userService.saveUser(user);
//    }
//
//    @PutMapping
//    public User update(@RequestBody User user){
//        return userService.updateUser(user);
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable int id){
//        try {
//            userService.deleteUser(id);
//            return "OK";
//        }catch (RuntimeException e ){
//           return e.getMessage();
//        }
//    }
}
