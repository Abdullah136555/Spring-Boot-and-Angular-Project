package com.linkup.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linkup.dao.PostRepository;
import com.linkup.model.Comment;
import com.linkup.model.Post;
import com.linkup.model.User;
import com.linkup.service.CommentService;
import com.linkup.service.PostService;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {
	
	 @Autowired
	 private PostRepository postRepository;
	
    @Autowired
    private PostService postService;
    
    private final Path uploadDir = Paths.get("uploads");
   
//    start comment
  @Autowired
  private CommentService commentService;

  
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

                                                                                                                                                                                            
 
    @PostMapping
    public ResponseEntity<Post> createPost(
        @RequestParam("content") String content,
        @RequestParam("postAuthor") String postAuthor,
        @RequestParam("postProfilePhoto") String postProfilePhoto,
        @RequestParam(value = "image", required = false) MultipartFile image) {

        Post post = new Post();
        post.setContent(content);
        post.setPostAuthor(postAuthor);
        post.setPostProfilePhoto(postProfilePhoto);

        if (image != null && !image.isEmpty()) {
            try {
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String originalFilename = image.getOriginalFilename();
                String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
                Path filePath = uploadDir.resolve(uniqueFilename);

                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                String imageUrl = "/uploads/" + uniqueFilename;
                post.setImageUrl(imageUrl);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }
        }

        Post savedPost = postService.createPost(post);
        return ResponseEntity.ok(savedPost);
    }

    
    
//    public ResponseEntity<Post> createPost(
//        @RequestParam("content") String content,
//        @RequestParam("postAuthor") String postAuthor,
//        @RequestParam("postProfilePhoto") String postProfilePhoto,
//        @RequestParam(value = "image", required = false) MultipartFile image) {
//    	 System.out.println("POST AUTHOR: " + postAuthor);
//         System.out.println("PROFILE PHOTO: " + postProfilePhoto);
//        Post post = new Post();
//        post.setContent(content);
//        post.setPostAuthor(postAuthor);                 
//        post.setPostProfilePhoto(postProfilePhoto);   
//       
//   
//
//        if (image != null && !image.isEmpty()) {
//            try {
//                if (!Files.exists(uploadDir)) {
//                    Files.createDirectories(uploadDir);
//                }
//
//                String filename = image.getOriginalFilename();
//                Path filePath = uploadDir.resolve(filename);
//                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                String imageUrl = "/uploads/" + filename;
//                post.setImageUrl(imageUrl);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ResponseEntity.status(500).build();
//            }
//        }
//
//        Post savedPost = postService.createPost(post);
//        return ResponseEntity.ok(savedPost);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Post post = postService.getPostById(id); 

        if (image != null && !image.isEmpty()) {
            try {
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String filename = image.getOriginalFilename();
                Path filePath = uploadDir.resolve(filename);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                String imageUrl = "/uploads/" + filename;
                post.setImageUrl(imageUrl);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }
        }

        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile imageFile) {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String filename = imageFile.getOriginalFilename();
            if (filename == null || filename.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid file name");
            }

            Path filePath = uploadDir.resolve(filename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/uploads/" + filename;

            //  Java 8 compatible response
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Could not upload file");
        }
}
   
    
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
//   comment 
    
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.addComment(postId, comment));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

  // ✅ Edit comment
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        Comment comment = commentService.updateComment(commentId, updatedComment);
        return ResponseEntity.ok(comment);
    }

    // ✅ Delete comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
    
}
