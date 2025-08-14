package com.linkup.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkup.dao.CommentRepository;
import com.linkup.dao.PostRepository;
import com.linkup.model.Comment;
import com.linkup.model.Post;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow();
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }
    
    public Comment updateComment(Long commentId, Comment updatedComment) {
        Comment existing = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));
        existing.setText(updatedComment.getText());
//        existing.setUpdatedAt(LocalDateTime.now()); // optional: if you track update time
        return commentRepository.save(existing);
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(commentId);
    }

    
}

