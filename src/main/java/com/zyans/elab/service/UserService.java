package com.zyans.elab.service;

import com.zyans.elab.domain.entity.User;
import com.zyans.elab.domain.form.UserForm;
import com.zyans.elab.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(long id) {
        LOGGER.debug("Getting user={}", id);
        return Optional.ofNullable(userRepository.findOne(id));
    }

    public Optional<User> getUserByUsername(String username) {
        LOGGER.debug("Getting user by username={}", username);
        return userRepository.findOneByUsername(username);
    }

    public long getIdByUsername(String username) {
        LOGGER.debug("Getting id by username={}", username);
        User user = getUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", username)));
        return user.getId();
    }

    public void deleteUser(String username) {
        User user = getUserByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", username)));
        userRepository.delete(user.getId());
    }

    public void create(UserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setOrganization(form.getOrganization());
        user.setRole(form.getRole());
        userRepository.save(user);
    }

    public void update(String username, UserForm form) {
        User user = userRepository.findOneByUsername(username).get();
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setOrganization(form.getOrganization());
        user.setRole(form.getRole());
        userRepository.save(user);
    }
}
