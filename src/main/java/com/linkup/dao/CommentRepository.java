package com.linkup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkup.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
}

