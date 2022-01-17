package com.pk.project_io.user;

import com.pk.project_io.UserGroup.UserGroup;
import com.pk.project_io.UserGroup.UserGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    public UserService(UserRepository userRepository, UserGroupRepository userGroupRepository) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public void createUser(Long groupId, User user) {
        Set<UserGroup> groups = new HashSet<>();
        UserGroup group = userGroupRepository.findById(groupId).orElseThrow(() -> new IllegalStateException("No such group found"));
        groups.add(group);
        user.setUserGroup(groups);
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long id, User userToUpdate) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such user found"));
        user.setEmail(userToUpdate.getEmail());
        user.setPassword(userToUpdate.getPassword());
        user.setUsername(userToUpdate.getUsername());
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("No such user found"));
        userRepository.delete(user);
    }

}
