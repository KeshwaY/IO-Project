package com.pk.project_io.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(User user) {
        repository.save(user);
    }

    public User getUserByName(String username) {
        return repository.findByUsername(username).get();
    }

}
