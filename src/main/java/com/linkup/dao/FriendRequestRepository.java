package com.linkup.dao;






import com.linkup.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    FriendRequest findBySenderUsernameAndReceiverUsername(String sender, String receiver);
    List<FriendRequest> findBySenderUsername(String sender);
    List<FriendRequest> findByReceiverUsername(String receiver);
}
