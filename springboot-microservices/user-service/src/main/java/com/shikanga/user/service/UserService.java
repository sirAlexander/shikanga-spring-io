package com.shikanga.user.service;

import com.shikanga.user.domain.Department;
import com.shikanga.user.domain.UserResponse;
import com.shikanga.user.entity.User;
import com.shikanga.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        log.info("Inside findUserById method of UserService");
        return userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse findUserWithDepartment(Long userId) {
        log.info("Inside findUserWithDepartment method of UserService");
        return userRepository.findById(userId)
                .map(this::buildUserResponse)
                .orElseThrow(NoSuchElementException::new);
    }

    private UserResponse buildUserResponse(User user) {
        final Department department = restTemplate.getForObject(
                "http://localhost:9001/departments/" + user.getDepartmentId(),
                Department.class);
        return UserResponse.builder()
                .user(user)
                .department(department)
                .build();
    }
}
