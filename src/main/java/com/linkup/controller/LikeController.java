package com.linkup.controller;



import com.linkup.model.Like;
import com.linkup.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "http://localhost:4200")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle")
    public String toggleLike(@RequestParam Long userId, @RequestParam Long postId) {
        return likeService.toggleLike(userId, postId);
    }

    @GetMapping("/count")
    public Long getLikeCount(@RequestParam Long postId) {
        return likeService.getLikeCount(postId);
    }

    @GetMapping("/isliked")
    public boolean isLikedByUser(@RequestParam Long userId, @RequestParam Long postId) {
        return likeService.isLikedByUser(userId, postId);
    }

    @GetMapping("/users")
    public List<Like> getUsersWhoLikedPost(@RequestParam Long postId) {
        return likeService.getUsersWhoLikedPost(postId);
    }
}




//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.linkup.model.Like;
//import com.linkup.model.Post;
//import com.linkup.service.LikeService;
//
//@RestController
//@RequestMapping("/api/likes")
//@CrossOrigin(origins = "http://localhost:4200")
//public class LikeController {
//
//    @Autowired
//    private LikeService likeService;
//
//    @PostMapping("/toggle")
//    public ResponseEntity<String> toggleLike(@RequestParam Long userId, @RequestParam Long postId) {
//        String message = likeService.toggleLike(userId, postId);
//        return ResponseEntity.ok(message);
//    }
//
//    @GetMapping("/count")
//    public ResponseEntity<Integer> getLikeCount(@RequestParam Long postId) {
//        int count = likeService.getLikeCount(postId);
//        return ResponseEntity.ok(count);
//       
//    }
//    @GetMapping
//    public List<Like> getAllPosts() {
//        return likeService.getAllLikes();
//    }
//    
//    @GetMapping("/isliked")
//    public ResponseEntity<Boolean> isPostLikedByUser(@RequestParam Long userId, @RequestParam Long postId) {
//        boolean isLiked = likeService.isPostLikedByUser(userId, postId);
//        return ResponseEntity.ok(isLiked);
//    }
//
//    
////    @GetMapping("/all")
////    public ResponseEntity<List<Like>> getAllLikes() {
////        List<Like> likes = likeService.getAllLikes();
////        return ResponseEntity.ok(likes);
////    }
//}
//
