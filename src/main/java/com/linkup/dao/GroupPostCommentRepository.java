package com.linkup.dao;



import com.linkup.model.GroupPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupPostCommentRepository extends JpaRepository<GroupPostComment, Long> {
    List<GroupPostComment> findByPostIdOrderByCreatedAtDesc(Long postId);
}
