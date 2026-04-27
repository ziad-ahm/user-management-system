package com.usermanagement.dashboard.Controller;

import com.usermanagement.dashboard.Entity.User;
import com.usermanagement.dashboard.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {

        userService.updateUser(id, user);
        return "User updated successfully";    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}