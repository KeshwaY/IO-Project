package com.pk.project_io.security.admin;

import com.pk.project_io.security.admin.dto.AdminSetRoleDto;
import com.pk.project_io.security.admin.dto.AdminSetRoleResponseDto;
import com.pk.project_io.security.roles.Role;
import com.pk.project_io.security.roles.RoleService;
import com.pk.project_io.security.roles.exceptions.RoleNotFoundException;
import com.pk.project_io.user.User;
import com.pk.project_io.user.UserRepository;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public AdminService(UserRepository userRepository, RoleService roleRepository) {
        this.userRepository = userRepository;
        this.roleService = roleRepository;
    }

    public AdminSetRoleResponseDto setUserRoles(AdminSetRoleDto adminSetRoleDto) throws UserNotFoundException, RoleNotFoundException {
        User user = userRepository.findByEmail(adminSetRoleDto.getEmail()).orElseThrow(UserNotFoundException::new);
        AdminSetRoleResponseDto responseDto = new AdminSetRoleResponseDto();
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
                        adminSetRoleDto.getEmail()
                ));
        userRepository.save(user);
        return responseDto;
    }

}
