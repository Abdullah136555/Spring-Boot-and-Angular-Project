package com.linkup.controller;



import com.linkup.dao.GroupRepository;
import com.linkup.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;
    
    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody Group group) {
        Optional<Group> existing = groupRepository.findByOwnerUserName(group.getOwnerUserName());
        if (existing.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("User already created a group");
        }

        if (group.getGroupId() == null || group.getGroupId().isEmpty()) {
            group.setGroupId(UUID.randomUUID().toString());
        }

        group.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(groupRepository.save(group));
    }


//    @PostMapping("/create")
//    public Group createGroup(@RequestBody Group group) {
//        if (group.getGroupId() == null || group.getGroupId().isEmpty()) {
//            group.setGroupId(UUID.randomUUID().toString());
//        }
//        group.setCreatedAt(LocalDateTime.now());
//        return groupRepository.save(group);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group updatedGroup) {
        return groupRepository.findById(id).map(group -> {
            group.setName(updatedGroup.getName());
            group.setDescription(updatedGroup.getDescription());
            group.setOwnerUserName(updatedGroup.getOwnerUserName());
            group.setProfilePhotoUrl(updatedGroup.getProfilePhotoUrl());
            group.setCoverPhotoUrl(updatedGroup.getCoverPhotoUrl());
            Group savedGroup = groupRepository.save(group);
            return ResponseEntity.ok(savedGroup);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String folder = "uploads/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(folder + fileName);

            // Make sure folder exists
            Files.createDirectories(filePath.getParent());

            Files.write(filePath, file.getBytes());

            String fileUrl = "http://localhost:8080/" + folder + fileName;
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    
    @GetMapping
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

