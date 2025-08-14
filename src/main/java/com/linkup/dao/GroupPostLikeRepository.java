package com.linkup.dao;





import com.linkup.model.GroupPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupPostLikeRepository extends JpaRepository<GroupPostLike, Long> {
    Optional<GroupPostLike> findByPostIdAndUsername(Long postId, String username);
    List<GroupPostLike> findByPostId(Long postId);
    long countByPostId(Long postId);
}

