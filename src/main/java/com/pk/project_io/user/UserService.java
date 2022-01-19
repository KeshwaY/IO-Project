package com.pk.project_io.user;

import com.pk.project_io.security.roles.Role;
import com.pk.project_io.security.roles.RoleService;
import com.pk.project_io.security.roles.exceptions.RoleNotFoundException;
import com.pk.project_io.security.roles.exceptions.ServerRoleNotFound;
import com.pk.project_io.user.dto.*;
import com.pk.project_io.user.exceptions.UserAlreadyExistsException;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import com.pk.project_io.user.exceptions.UserPropertyUpdateException;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;

    public UserService(
            UserRepository userRepository,
            RoleService roleService,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.emailValidator = new EmailValidator();
    }

    @Transactional
    public UserGetDto createUser(UserPostDto userPostDto) throws ServerRoleNotFound, UserAlreadyExistsException {
        userPostDto.setEmail(userPostDto.getEmail().toLowerCase(Locale.ROOT));
        checkIfUserAlreadyExists(userPostDto);
        User user = userMapper.userPostDtoToUser(userPostDto);
        addUserRoleToUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setEnabled(true);
        userRepository.save(user);
        return userMapper.userToUserGetDto(user);
    }

    private void checkIfUserAlreadyExists(UserPostDto userPostDto) throws UserAlreadyExistsException {
        Optional<User> userFromDatabaseByEmail = userRepository.findByEmail(userPostDto.getEmail());
        Optional<User> userFromDatabaseByName = userRepository.findByUsername(userPostDto.getUsername());
        if (userFromDatabaseByEmail.isPresent() || userFromDatabaseByName.isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }

    private void addUserRoleToUser(User user) throws ServerRoleNotFound {
        String userRole = "USER";
        if (userRepository.findAll().size() == 0) {
            userRole = "ADMINISTRATOR";
        }
        try {
            Role role = roleService.addUserToRole(user, userRole);
            user.setRoles(
                    Set.of(role)
            );
        } catch (RoleNotFoundException e) {
            throw new ServerRoleNotFound();
        }
    }

    public UpdateResponseDto updateUserProperty(String email, String property, UpdatePropertyPostDto updatePropertyPostDto) throws UserNotFoundException, UserPropertyUpdateException {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        UpdateResponseDto updateResponseDto = new UpdateResponseDto();
        updateProperty(user, property, updatePropertyPostDto);
        if (property.equals("password")) {
            updateResponseDto.setStatus("Password updated");
        } else {
            updateResponseDto.setStatus(String.format("%s set to %s",
                    property,
                    updatePropertyPostDto.getNewValue())
            );
        }
        userRepository.save(user);
        return updateResponseDto;
    }

    private void updateProperty(User user, String property, UpdatePropertyPostDto updatePropertyPostDto) throws UserPropertyUpdateException {
        switch (property) {
            case "email":
                if (canUpdateEmail(updatePropertyPostDto.getNewValue())) {
                    user.setEmail(updatePropertyPostDto.getNewValue());
                } else {
                    throw new UserPropertyUpdateException();
                }
                break;
            case "username":
                if (canUpdateUsername(updatePropertyPostDto.getNewValue())) {
                    user.setUsername(updatePropertyPostDto.getNewValue());
                } else {
                    throw new UserPropertyUpdateException();
                }
                break;
            case "password":
                if (canUpdatePassword(updatePropertyPostDto.getNewValue())) {
                    user.setPassword(passwordEncoder.encode(updatePropertyPostDto.getNewValue()));
                } else {
                    throw new UserPropertyUpdateException();
                }
                break;
        }
    }

    private boolean canUpdateEmail(@Email String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    private boolean canUpdateUsername(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    private boolean canUpdatePassword(String password) {
        return password.length() >= 6 && password.length() <= 50;
    }

    public User getRawUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User getRawUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public UserGetDto getReadOnlyUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return userMapper.userToUserGetDto(user);
    }

    public List<UserGetDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(userMapper::userToUserGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

}
