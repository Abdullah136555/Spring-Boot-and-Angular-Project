package com.linkup.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "post_author")
    private String postAuthor;

    @Column(name = "post_profile_photo")
    private String postProfilePhoto;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ✅ Optional: createdAt initialize automatically
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getter and Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public String getPostProfilePhoto() {
        return postProfilePhoto;
    }

    public void setPostProfilePhoto(String postProfilePhoto) {
        this.postProfilePhoto = postProfilePhoto;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ✅ Constructor (optional)
    public Post() {
    }

    public Post(String content, String imageUrl, String postAuthor, String postProfilePhoto) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.postAuthor = postAuthor;
        this.postProfilePhoto = postProfilePhoto;
        this.createdAt = LocalDateTime.now();
    }
}
