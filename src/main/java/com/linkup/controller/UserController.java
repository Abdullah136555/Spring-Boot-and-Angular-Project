package com.linkup.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.linkup.dao.UserDAO;
import com.linkup.model.User;
import com.linkup.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(value = "*")
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> foundOpt = userDAO.getByUsername(user.getUsername());
        if (foundOpt.isPresent()) {
            User found = foundOpt.get();
            if (found.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok(found);
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

 // Store in current project root/uploads/
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return uploadFileAndSaveToUser(file, userId, true);
    }

    @PostMapping("/cover-photo")
    public ResponseEntity<String> uploadCoverPhoto(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return uploadFileAndSaveToUser(file, userId, false); 
    }

    private ResponseEntity<String> uploadFileAndSaveToUser(MultipartFile file, Long userId, boolean isProfilePhoto) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());

            User user = userService.getUserById(userId).orElse(null);
            if (user != null) {
                if (isProfilePhoto) {
                    user.setProfilePhoto(fileName);
                } else {
                    user.setCoverPhoto(fileName);
                }
                userService.createUser(user); 
                return ResponseEntity.ok(fileName);
            } else {
                return ResponseEntity.status(404).body("User not found");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File upload failed");
        }
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User user) {
        try {
            User updated = userService.updateUser(id, user);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); 
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping
    public ResponseEntity<User> createUserProfile(@RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers(); 
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/usernames")
    public List<String> getAllUsernames() {
        return userDAO.findAll()
            .stream()
            .map(User::getUsername)
            .collect(Collectors.toList());
    }

}

