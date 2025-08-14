//package com.linkup.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.linkup.model.Comment;
//import com.linkup.service.CommentService;
//import com.linkup.service.PostService;
//
//
//@RestController
//@RequestMapping("/api/comments")
//@CrossOrigin(origins = "http://localhost:4200") // এটি গুরুত্বপূর্ণ
//public class CommentController {
//@Autowired
//private PostService postService;
//// start comment
//    @Autowired
//    private CommentService commentService;
//
//    @PostMapping("/{postId}/comments")
//    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment) {
//        return ResponseEntity.ok(commentService.addComment(postId, comment));
//    }
//
//    @GetMapping("/{postId}/comments")
//    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
//        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
//    }
// 
// // ✅ Edit comment
//    @PutMapping("/comments/{commentId}")
//    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
//        Comment comment = commentService.updateComment(commentId, updatedComment);
//        return ResponseEntity.ok(comment);
//    }
//
//    // ✅ Delete comment
//    @DeleteMapping("/comments/{commentId}")
//    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
//        commentService.deleteComment(commentId);
//        return ResponseEntity.ok().build();
//    }
//    
//
//}

