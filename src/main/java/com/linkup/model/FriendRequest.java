package com.linkup.model;

import java.time.LocalDateTime;
import java.util.Date;

import com.linkup.enums.FriendRequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "friend_request")
public class FriendRequest {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderUsername;
    private String senderProfilePhoto;

    private String receiverUsername;
    private String receiverProfilePhoto;

    private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getSenderProfilePhoto() {
		return senderProfilePhoto;
	}

	public void setSenderProfilePhoto(String senderProfilePhoto) {
		this.senderProfilePhoto = senderProfilePhoto;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	public String getReceiverProfilePhoto() {
		return receiverProfilePhoto;
	}

	public void setReceiverProfilePhoto(String receiverProfilePhoto) {
		this.receiverProfilePhoto = receiverProfilePhoto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
