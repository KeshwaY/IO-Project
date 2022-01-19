package com.pk.project_io.security.admin.user;

import com.pk.project_io.security.admin.user.dto.AdminDeleteUserResponseDto;
import com.pk.project_io.security.admin.user.dto.AdminSetResponseDto;
import com.pk.project_io.security.admin.user.dto.AdminSetRoleDto;
import com.pk.project_io.security.admin.user.dto.AdminSetUserPropertyDto;
import com.pk.project_io.security.roles.Role;
import com.pk.project_io.security.roles.RoleService;
import com.pk.project_io.security.roles.exceptions.RoleNotFoundException;
import com.pk.project_io.user.User;
import com.pk.project_io.user.UserService;
import com.pk.project_io.user.dto.UpdatePropertyPostDto;
import com.pk.project_io.user.dto.UpdateResponseDto;
import com.pk.project_io.user.dto.UserGetDto;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import com.pk.project_io.user.exceptions.UserPropertyUpdateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminUserService {

    private final UserService userService;
    private final RoleService roleService;

    public AdminUserService(UserService userService, RoleService roleRepository) {
        this.userService = userService;
        this.roleService = roleRepository;
    }

    public AdminSetResponseDto setUserRoles(String userEmail, AdminSetRoleDto adminSetRoleDto) throws UserNotFoundException, RoleNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        AdminSetResponseDto responseDto = new AdminSetResponseDto();
        if (adminSetRoleDto.getAction().equals("GRANT")) {
            Role role = roleService.addUserToRole(user, adminSetRoleDto.getRole());
            user.getRoles().add(role);
        } else {
            Role role = roleService.removeUserFromRole(user, adminSetRoleDto.getRole());
            user.getRoles().remove(role);
        }
        responseDto.setStatus(
                String.format("Successfully performed %s %s on user %s",
                        adminSetRoleDto.getAction(),
                        adminSetRoleDto.getRole(),
                        userEmail
                ));
        userService.saveUser(user);
        return responseDto;
    }

    @Transactional
    public AdminDeleteUserResponseDto deleteUser(String email) throws UserNotFoundException {
        User user = userService.getRawUserByEmail(email);
        for (Role role : user.getRoles()) {
            role.getUsers().remove(user);
            roleService.saveRole(role);
        }
        userService.deleteUser(user);
        return new AdminDeleteUserResponseDto(String.format("Successfully removed %s!", email));
    }

    public UserGetDto getUser(String userEmail) throws UserNotFoundException {
        return userService.getReadOnlyUserByEmail(userEmail);
    }

    public List<UserGetDto> getAllUsers() {
        return userService.getAllUsers();
    }

    public AdminSetResponseDto setUserProperty(String userEmail, String property, AdminSetUserPropertyDto setUserPropertyDto) throws UserNotFoundException, UserPropertyUpdateException {
        UpdateResponseDto updateResponseDto = userService.updateUserProperty(
                userEmail,
                property,
                new UpdatePropertyPostDto(setUserPropertyDto.getNewValue())
        );
        return new AdminSetResponseDto(userEmail, updateResponseDto.getStatus());
    }

}
