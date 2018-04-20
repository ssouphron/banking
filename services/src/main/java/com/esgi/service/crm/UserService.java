package com.esgi.service.crm;

import com.esgi.crm.User;
import com.esgi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewUser(User newUser) throws Exception {
        notNull(newUser, "User must not be null");

        Optional<User> existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("Another user exists with the same email address");
        }

        userRepository.save(newUser);
    }

    public User getUser(final Integer userId) {
        notNull(userId, "userId must not be null");

        return userRepository.getOne(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
