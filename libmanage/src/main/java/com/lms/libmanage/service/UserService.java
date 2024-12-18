package com.lms.libmanage.service;

import com.lms.libmanage.entity.user.User;
import com.lms.libmanage.entity.user.UserRequest;
import com.lms.libmanage.entity.user.UserResponse;
import com.lms.libmanage.entity.user.UserUpdateRequest;
import com.lms.libmanage.exception.NotFoundException;
import com.lms.libmanage.repository.UserRepository;
import com.lms.libmanage.utils.DuplicateEntryException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    private  void checkIfUserUnique(String username, String email) {
        userRepository.findByUsername(username)
                .ifPresent(u-> {throw new DuplicateEntryException("Username " + username + " is already taken.");});
        userRepository.findByEmail(email)
                .ifPresent(u-> {throw new DuplicateEntryException("An account using that email address already exists.");});
    }

    public List<UserResponse> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getAllLibraryMembers() {
        return userRepository
                .getAllLibraryMembers()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getAllLibraryStaff() {
        return userRepository
                .getAllLibraryStaff()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User userDetails) {
        User user = getUserById(id);
        user.setFirstname(userDetails.getFirstname());
        user.setLastname(userDetails.getLastname());
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAddress(userDetails.getAddress());
        return userRepository.save(user);
    }

    public String deleteUser(Integer id){
        var us = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User with id " + id + " does not exist!"));
        userRepository.deleteById(id);
        return "User with id " + id + " is successfully deleted!";
    }

    public String deleteUserByUUID(String uuid){
        var us = userRepository.findByUUID(uuid).orElseThrow(()-> new EntityNotFoundException("User with id " + uuid + " does not exist!"));
        userRepository.delete(us);
        return "User with UUID " + uuid + " is successfully deleted!";
    }

    public User findUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User with id " + userId + " does not exist!"));
        return user;
    }
}
