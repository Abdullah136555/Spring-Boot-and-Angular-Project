package com.linkup.controller;





import com.linkup.dao.FriendRequestRepository;
import com.linkup.model.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/friend-requests")
@CrossOrigin(origins = "http://localhost:4200")
public class FriendRequestController {

    @Autowired
    private FriendRequestRepository repo;

    @PostMapping("/send")
    public FriendRequest sendRequest(@RequestBody FriendRequest request) {
        FriendRequest existing = repo.findBySenderUsernameAndReceiverUsername(request.getSenderUsername(), request.getReceiverUsername());
        if (existing != null) return existing;

        request.setStatus("pending");
        return repo.save(request);
    }

    
    @PostMapping("/accept")
    public FriendRequest acceptRequest(@RequestParam Long requestId) {
        FriendRequest request = repo.findById(requestId).orElseThrow();
        request.setStatus("accepted");
        return repo.save(request);
    }

    @PostMapping("/reject")
    public FriendRequest rejectRequest(@RequestParam Long requestId) {
        FriendRequest request = repo.findById(requestId).orElseThrow();
        request.setStatus("rejected");
        return repo.save(request);
    }

    @GetMapping("/pending/{username}")
    public List<FriendRequest> getPendingRequests(@PathVariable String username) {
        return repo.findByReceiverUsername(username)
                .stream()
                .filter(r -> "pending".equalsIgnoreCase(r.getStatus()))
                .toList();
    }

    @GetMapping("/friends/{username}")
    public List<FriendRequest> getFriends(@PathVariable String username) {
        List<FriendRequest> sent = repo.findBySenderUsername(username);
        List<FriendRequest> received = repo.findByReceiverUsername(username);

        List<FriendRequest> accepted = new ArrayList<>();
        for (FriendRequest r : sent) {
            if ("accepted".equalsIgnoreCase(r.getStatus())) accepted.add(r);
        }
        for (FriendRequest r : received) {
            if ("accepted".equalsIgnoreCase(r.getStatus())) accepted.add(r);
        }
        return accepted;
    }
}
