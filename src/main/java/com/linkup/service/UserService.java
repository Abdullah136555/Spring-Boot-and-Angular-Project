package com.linkup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.linkup.dao.UserDAO;
import com.linkup.model.User;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    public User createUser(User user) {
        return userDAO.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userDAO.findById(id).map(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setBio(updatedUser.getBio());
            existingUser.setWork(updatedUser.getWork());
            existingUser.setEducation(updatedUser.getEducation());
            existingUser.setHometown(updatedUser.getHometown());
            existingUser.setCurrentCity(updatedUser.getCurrentCity());
            existingUser.setRelationshipStatus(updatedUser.getRelationshipStatus());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setWebsite(updatedUser.getWebsite());
            existingUser.setProfilePhoto(updatedUser.getProfilePhoto());
            existingUser.setCoverPhoto(updatedUser.getCoverPhoto());
//            existingUser.setCreatedAt(updatedUser.getCreatedAt());
            return userDAO.update(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public void deleteUser(Long id) {
        if (!userDAO.findById(id).isPresent()) {
            throw new RuntimeException("User not found with id " + id);
        }
        userDAO.deleteById(id);
    }

}
