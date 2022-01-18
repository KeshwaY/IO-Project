package com.pk.project_io.security.admin;

import com.pk.project_io.security.admin.dto.AdminSetRoleDto;
import com.pk.project_io.security.admin.dto.AdminSetRoleResponseDto;
import com.pk.project_io.security.roles.exceptions.RoleNotFoundException;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("user/role")
    private ResponseEntity<AdminSetRoleResponseDto> setUserRole(
            @RequestBody @Valid AdminSetRoleDto adminSetRoleDto
    ) throws UserNotFoundException, RoleNotFoundException {
        AdminSetRoleResponseDto responseDto = this.adminService.setUserRoles(adminSetRoleDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("user")
    private ResponseEntity<String> test() {
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

}
