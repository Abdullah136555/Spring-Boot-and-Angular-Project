package com.linkup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkup.dao.LikeRepository;
import com.linkup.dao.PostRepository;
import com.linkup.dao.UserDAO;
import com.linkup.model.Like;
import com.linkup.model.Post;
import com.linkup.model.User;

import jakarta.transaction.Transactional;

@Service
public class LikeService {
	@Autowired
	private UserDAO userDAO;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    
//    
//    public String toggleLike(Long userId, Long postId) {
//        Optional<User> userOpt = userDAO.findById(userId);
//        Optional<Post> postOpt = postRepository.findById(postId);
//
//        if (!userOpt.isPresent()) {
//            throw new RuntimeException("User not found with id: " + userId);
//        }
//        if (!postOpt.isPresent()) {
//            throw new RuntimeException("Post not found with id: " + postId);
//        }
//
//        // Check if Like already exists
//        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
//
//        if (existingLike.isPresent()) {
//            likeRepository.deleteByUserIdAndPostId(userId, postId);
//            return "Unliked";
//        } else {
//            Like like = new Like();
//            like.setUserId(userId);
//            like.setPostId(postId);
//            likeRepository.save(like);
//            return "Liked";
//        }
//    }
    @Transactional
    public String toggleLike(Long userId, Long postId) {
        Optional<User> userOpt = userDAO.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        if (!postOpt.isPresent()) {
            throw new RuntimeException("Post not found with id: " + postId);
        }

        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, postId);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());  // এই লাইনটা try করো
            return "Unliked";
        } else {
            Like like = new Like();
            like.setUserId(userId);
            like.setPostId(postId);
            likeRepository.save(like);
            return "Liked";
        }
    }



//    public String toggleLike(Long userId, Long postId) {
//        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
//
//        if (existingLike.isPresent()) {
//            likeRepository.deleteByUserIdAndPostId(userId, postId);
//            return "Unliked";
//        } else {
//            Like like = new Like();
//            like.setUserId(userId);
//            like.setPostId(postId);
//            likeRepository.save(like);
//            return "Liked";
//        }
//    }

    public Long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public boolean isLikedByUser(Long userId, Long postId) {
        return likeRepository.findByUserIdAndPostId(userId, postId).isPresent();
    }

    public List<Like> getUsersWhoLikedPost(Long postId) {
        return likeRepository.findByPostId(postId);
    }
}


//import java.util.List;
//import java.util.Optional;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.linkup.dao.LikeRepository;
//import com.linkup.dao.PostRepository;
//import com.linkup.dao.UserDAO;
//import com.linkup.model.Like;
//import com.linkup.model.Post;
//import com.linkup.model.User;
//
//@Service
//public class LikeService {
//
//    @Autowired
//    private LikeRepository likeRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private UserDAO userRepository;
//
//    public String toggleLike(Long userId, Long postId) {
//        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, postId);
//
//        if (existingLike.isPresent()) {
//            // Unlike
//            likeRepository.delete(existingLike.get());
//            return "Unliked";
//        } else {
//            // Like
//            Post post = postRepository.findById(postId)
//                    .orElseThrow(() -> new RuntimeException("Post not found"));
//            User user = userRepository.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            Like like = new Like();
//            like.setUser(user);
//            like.setPost(post);
//            likeRepository.save(like);
//            return "Liked";
//        }
//    }
//
//  
//    public List<Like> getAllLikes() {
//        return likeRepository.findAll();
//    }
//  
//    
//    
//    public int getLikeCount(Long postId) {
//        return likeRepository.countByPostId(postId);
//    }
//    
//    public boolean isPostLikedByUser(Long userId, Long postId) {
//        return likeRepository.existsByUserIdAndPostId(userId, postId);
//    }
//
//}
