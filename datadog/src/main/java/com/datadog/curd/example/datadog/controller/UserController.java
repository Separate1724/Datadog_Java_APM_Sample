package com.datadog.curd.example.datadog.controller;

import com.datadog.curd.example.datadog.entity.User;
import com.datadog.curd.example.datadog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger Logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    // Get All Users Information
    @GetMapping({"", "/"})
    public List<User> getAllUsers() {
        Logger.info("getAllUsers called in UserController");
        return userRepository.findAllUsers();
    }

    // Get User Information By Name
    @GetMapping("/search")
    public List<User> findByName(@RequestParam String name) {
        return userRepository.findByNameNative(name);
    }

    // Get User Information By Email
    @GetMapping("/search/email")
    public List<User> findByEmail(@RequestParam String email) {
        return userRepository.findByEmailNative(email);
    }

    // Add User Information
    @PostMapping
    public String addUser(@RequestParam String name, @RequestParam String email) {
        int rowsAffected = userRepository.insertUser(name, email);
        if (rowsAffected > 0) {
            return "User added successfully";
        } else {
            return "Failed to add user";
        }
    }

    // Delete User Information
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        int rowsAffected = userRepository.deleteUser(id);
        if (rowsAffected > 0) {
            return "User deleted successfully";
        } else {
            return "Failed to delete user";
        }
    }
}