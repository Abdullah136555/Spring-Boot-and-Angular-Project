package com.linkup.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkup.model.Post;

import jakarta.transaction.Transactional;


@Repository(value="postRepository")
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
	 // List<Post> findByUserId(Long userId);
}
