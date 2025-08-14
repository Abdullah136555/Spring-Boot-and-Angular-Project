//package com.linkup.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.linkup.dao.PostRepository;
//import com.linkup.model.Post;
//
//@Service
//public class PostService {
//    @Autowired
//    private PostRepository postRepository;
//
//    public List<Post> getAllPosts() {
//        return postRepository.findAll();
//    }
//
//    public Post createPost(Post post) {
//        post.setCreatedAt(LocalDateTime.now());
//        return postRepository.save(post);
//    }
//
//// // PostService.java
////    public Post createPost(Post post) {
////        return postRepository.save(post);
////    }
//
//   
//
//    public Post updatePost(Long id, Post postData) {
//        Post post = postRepository.findById(id)
//                      .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));
//        post.setContent(postData.getContent());
//        post.setImageUrl(postData.getImageUrl());
//        return postRepository.save(post);
//    }
//
//    
////    public Post updatePost(Long id, Post postData) {
////        Post post = postRepository.findById(id).orElseThrow(null);
////        post.setContent(postData.getContent());
////        post.setImageUrl(postData.getImageUrl());
////        return postRepository.save(post);
////    }
//
//    public void deletePost(Long id) {
//        postRepository.deleteById(id);
//    }
//}




package com.linkup.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkup.dao.PostRepository;
import com.linkup.model.Post;

@Service
public class PostService {
	
	
    @Autowired
    private PostRepository postRepository;

    
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + id));
    }

    public Post updatePost(Long id, Post postData) {
        Post post = getPostById(id);
        
        // Update only non-null fields (optional safety)
        if (postData.getContent() != null) {
            post.setContent(postData.getContent());
        }
        if (postData.getImageUrl() != null) {
            post.setImageUrl(postData.getImageUrl());
        }

        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

