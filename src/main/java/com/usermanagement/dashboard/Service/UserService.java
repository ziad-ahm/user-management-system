package com.usermanagement.dashboard.Service;

import com.usermanagement.dashboard.Entity.User;
import com.usermanagement.dashboard.Repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ➜ Create User
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    // ➜ Get by ID
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ➜ Get all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // ➜ Update user
    public User updateUser(Long id, User updatedUser) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updatedUser.getUsername());

        // ✔️ update password only if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setJobTitle(updatedUser.getJobTitle());
        user.setSalary(updatedUser.getSalary());
        user.setRole(updatedUser.getRole());

        return userRepo.save(user);
    }

    // ➜ Delete user
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById(id);
    }

    // ➜ Get by username
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}