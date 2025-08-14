package com.linkup.controller;

import com.linkup.dao.GroupPostCommentRepository;
import com.linkup.model.GroupPostComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-posts/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupPostCommentController {

    @Autowired
    private GroupPostCommentRepository commentRepo;

    @PostMapping("/{postId}")
    public GroupPostComment createComment(@PathVariable Long postId, @RequestBody GroupPostComment comment) {
        comment.setPostId(postId);
        return commentRepo.save(comment);
    }

    @GetMapping("/{postId}")
    public List<GroupPostComment> getComments(@PathVariable Long postId) {
        return commentRepo.findByPostIdOrderByCreatedAtDesc(postId);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<GroupPostComment> updateComment(@PathVariable Long commentId, @RequestBody GroupPostComment updated) {
        return commentRepo.findById(commentId)
            .map(comment -> {
                comment.setContent(updated.getContent());
                return ResponseEntity.ok(commentRepo.save(comment));
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentRepo.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }
}

