package com.pk.project_io.user;

import com.pk.project_io.UserGroup.UserGroup;
import com.pk.project_io.UserGroup.UserGroupRepository;
import com.pk.project_io.security.roles.Role;
import com.pk.project_io.security.roles.RoleRepository;
import com.pk.project_io.security.roles.RoleService;
import com.pk.project_io.security.roles.exceptions.RoleNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserGroupRepository userGroupRepository,
            PasswordEncoder passwordEncoder,
            RoleService roleService
    ) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    public void createUser(Long groupId, User user) throws RoleNotFoundException {
        UserGroup group = userGroupRepository.findById(groupId).orElseThrow(() -> new IllegalStateException("No such group found"));
        Role role = roleService.addUserToRole(user, "USER");
        user.setUserGroup(
                Set.of(group)
        );
        user.setRoles(
            Set.of(role)
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByName(String username) {
        return userRepository.findByEmail(username).get();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long id, User userToUpdate) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such user found"));
        user.setEmail(userToUpdate.getEmail());
        updatePassword(user, user.getPassword());
        user.setUsername(userToUpdate.getUsername());
        userRepository.save(user);
    }

    private void updatePassword(User user, String newPassword) {
        if (!passwordEncoder.matches(newPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("No such user found"));
        userRepository.delete(user);
    }

}
