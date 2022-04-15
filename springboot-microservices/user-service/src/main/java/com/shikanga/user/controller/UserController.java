package com.shikanga.user.controller;

import com.shikanga.user.domain.UserResponse;
import com.shikanga.user.entity.User;
import com.shikanga.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User saveUser(@RequestBody User user){
        log.info("Inside saveUser method of UserController");
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> findAllUsers() {
        log.info("Inside findAllUsers method of UserController");
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse findUserWithDepartment(@PathVariable("id") Long userId) {
        log.info("Inside findUserWithDepartment method of UserController");
        return userService.findUserWithDepartment(userId);
    }

}
