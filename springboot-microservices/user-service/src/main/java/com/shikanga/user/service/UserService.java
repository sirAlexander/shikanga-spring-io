package com.shikanga.user.service;

import com.shikanga.user.entity.User;
import com.shikanga.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public User findUserById(Long userId) {
        log.info("Inside findUserById method of UserService");
        return  userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
