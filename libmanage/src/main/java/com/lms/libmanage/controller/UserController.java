package com.lms.libmanage.controller;

import com.lms.libmanage.entity.user.User;
import com.lms.libmanage.entity.user.UserResponse;
import com.lms.libmanage.entity.user.UserUpdateRequest;
import com.lms.libmanage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/library-member")
    public ResponseEntity<List<UserResponse>> getAllLibraryMembers() {
        return ResponseEntity.ok(userService.getAllLibraryMembers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/library-staff")
    public ResponseEntity<List<UserResponse>> getAllLibraryStaff() {
        return ResponseEntity.ok(userService.getAllLibraryStaff());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/uuid/{uuid}")
    public ResponseEntity<String> deleteUserByUUID(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.deleteUserByUUID(uuid));
    }


    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
}
