package com.linkup.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linkup.dao.VideoRepository;
import com.linkup.model.Video;

//
//import com.linkup.dao.VideoRepository;
//import com.linkup.model.Video;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/videos")
//@CrossOrigin(origins = "*")
//public class VideoController {
//
//    @Autowired
//    private VideoRepository videoRepository;
//
//    @PostMapping("/upload")
//    public Video uploadVideo(@RequestBody Video video) {
//        return videoRepository.save(video);
//    }
//
//    @GetMapping
//    public List<Video> getAllVideos() {
//        return videoRepository.findAll();
//    }
//}

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    private final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("type") String type,
        @RequestParam("userId") Long userId,
        @RequestParam("userName") String userName,
        @RequestParam("profilePhoto") String profilePhoto,
        @RequestParam(value = "videoFile", required = false) MultipartFile file,
        @RequestParam(value = "youtubeUrl", required = false) String youtubeUrl
    ) throws IOException {
        String videoUrl;

        if (type.equals("youtube")) {
            videoUrl = youtubeUrl;
        } else {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            videoUrl = "/uploads/" + filename;
        }

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setType(type);
        video.setVideoUrl(videoUrl);
        video.setUserId(userId);
        video.setUserName(userName);               // ✅ Add this
        video.setProfilePhoto(profilePhoto);       // ✅ Add this

        videoRepository.save(video);

        return ResponseEntity.ok(video);
    }


    @GetMapping
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVideo(@PathVariable Long id, @RequestBody Video video) {
        Video existing = videoRepository.findById(id).orElse(null);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setTitle(video.getTitle());
        existing.setDescription(video.getDescription());
        existing.setVideoUrl(video.getVideoUrl());
        existing.setType(video.getType());
        videoRepository.save(existing);

        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
    	videoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
