package com.linkup.controller;



import com.linkup.dao.GroupPostRepository;
import com.linkup.model.GroupPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/group-posts")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupPostController {

    @Autowired
    private GroupPostRepository postRepository;

    @PostMapping("/create")
    public GroupPost createPost(@RequestBody GroupPost post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @GetMapping("/group/{groupId}")
    public List<GroupPost> getPostsByGroup(@PathVariable String groupId) {
        return postRepository.findByGroupId(groupId);
    }

    @PostMapping("/upload-media")
    public ResponseEntity<String> uploadMedia(@RequestParam("file") MultipartFile file) {
        try {
            String folder = "uploads/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(folder + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok("http://localhost:8080/uploads/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GroupPost> updatePost(@PathVariable Long id, @RequestBody GroupPost updatedPost) {
        return postRepository.findById(id)
            .map(existingPost -> {
                existingPost.setContent(updatedPost.getContent());
                existingPost.setMediaUrl(updatedPost.getMediaUrl());
                return ResponseEntity.ok(postRepository.save(existingPost));
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

