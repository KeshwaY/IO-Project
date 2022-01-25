package com.pk.project_io.security;

import com.pk.project_io.security.roles.Role;
import com.pk.project_io.security.roles.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public ApplicationStartupListener(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(createRole("ADMINISTRATOR", 1));
        roleSet.add(createRole("USER", 2));

        Set<String> rolesToCreateNames = Set.of("ADMINISTRATOR", "USER");
        Set<String> rolesInSystemNames = roleRepository.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toUnmodifiableSet());

        if (!rolesInSystemNames.containsAll(rolesToCreateNames)) {
            roleRepository.saveAll(roleSet);
        }
    }

    private Role createRole(String name, int level) {
        Role role = new Role();
        role.setName(name);
        role.setLevel(level);
        return role;
    }

}
