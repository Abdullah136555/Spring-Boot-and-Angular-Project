package com.linkup.controller;


import com.linkup.dao.GroupPostLikeRepository;
import com.linkup.model.GroupPostLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/group-posts/likes")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupPostLikeController {

    @Autowired
    private GroupPostLikeRepository likeRepo;

    @PostMapping("/{postId}/{username}")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId, @PathVariable String username) {
        Optional<GroupPostLike> existing = likeRepo.findByPostIdAndUsername(postId, username);
        if (existing.isPresent()) {
            likeRepo.delete(existing.get());
            return ResponseEntity.ok("Unliked");
        } else {
            likeRepo.save(new GroupPostLike(postId, username));
            return ResponseEntity.ok("Liked");
        }
    }

    @GetMapping("/count/{postId}")
    public long countLikes(@PathVariable Long postId) {
        return likeRepo.countByPostId(postId);
    }

    @GetMapping("/liked/{postId}/{username}")
    public boolean isLiked(@PathVariable Long postId, @PathVariable String username) {
        return likeRepo.findByPostIdAndUsername(postId, username).isPresent();
    }
}

