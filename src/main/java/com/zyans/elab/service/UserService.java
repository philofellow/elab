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

    public User getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email);
        return userRepository.findOneByEmail(email).
                orElseThrow(()->new NoSuchElementException("user with email" + email + "not exist"));
    }

    public long getIdByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email);
        return getUserByEmail(email).getId();
    }

    public void deleteUser(String email) {
        User user = getUserByEmail(email);
        userRepository.delete(user.getId());
    }

    public void create(UserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setOrganization(form.getOrganization());
        user.setRole(form.getRole());
        userRepository.save(user);
    }

    public void update(String email, UserForm form) {
        User user = getUserByEmail(email);
        user.setEmail(form.getEmail());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setOrganization(form.getOrganization());
        user.setRole(form.getRole());
        userRepository.save(user);
    }
}
