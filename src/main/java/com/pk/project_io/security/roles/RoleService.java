package com.pk.project_io.security.roles;

import com.pk.project_io.security.roles.exceptions.RoleNotFoundException;
import com.pk.project_io.user.User;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role addUserToRole(User user, String roleName) throws RoleNotFoundException {
        Role role = roleRepository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
        role.getUsers().add(user);
        return role;
    }

    public Role removeUserFromRole(User user, String roleName) throws RoleNotFoundException {
        Role role = roleRepository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
        role.getUsers().remove(user);
        return role;
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

}
