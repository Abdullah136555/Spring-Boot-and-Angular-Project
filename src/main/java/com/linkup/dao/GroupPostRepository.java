package com.linkup.dao;


import com.linkup.model.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {
    List<GroupPost> findByGroupId(String groupId);
}

