package com.pk.project_io.security.admin.user;

import com.pk.project_io.security.admin.user.dto.AdminDeleteUserResponseDto;
import com.pk.project_io.security.admin.user.dto.AdminSetResponseDto;
import com.pk.project_io.security.admin.user.dto.AdminSetRoleDto;
import com.pk.project_io.security.admin.user.dto.AdminSetUserPropertyDto;
import com.pk.project_io.security.roles.exceptions.RoleNotFoundException;
import com.pk.project_io.user.dto.UserGetDto;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import com.pk.project_io.user.exceptions.UserPropertyUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final AdminUserService adminService;

    public AdminUserController(AdminUserService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{user_email}")
    public ResponseEntity<UserGetDto> getUserData(
            @PathVariable("user_email") String userEmail
    ) throws UserNotFoundException {
        UserGetDto userGetDto = adminService.getUser(userEmail);
        return new ResponseEntity<>(userGetDto, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserGetDto>> getAllUsersData() {
        List<UserGetDto> userGetDtos = adminService.getAllUsers();
        return new ResponseEntity<>(userGetDtos, HttpStatus.OK);
    }

    @PutMapping(value = "/{user_email}", params = {"property"})
    public ResponseEntity<AdminSetResponseDto> setUserProperty(
            @PathVariable("user_email") String userEmail,
            @RequestParam String property,
            @RequestBody @Valid AdminSetUserPropertyDto setUserPropertyDto
    ) throws UserNotFoundException, UserPropertyUpdateException {
        AdminSetResponseDto responseDto = adminService.setUserProperty(userEmail, property, setUserPropertyDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{user_email}/roles")
    public ResponseEntity<AdminSetResponseDto> setUserRole(
            @PathVariable("user_email") String userEmail,
            @RequestBody @Valid AdminSetRoleDto adminSetRoleDto
    ) throws UserNotFoundException, RoleNotFoundException {
        AdminSetResponseDto responseDto = adminService.setUserRoles(userEmail, adminSetRoleDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{user_email}")
    public ResponseEntity<AdminDeleteUserResponseDto> deleteUser(
            @PathVariable("user_email") String userEmail
    ) throws UserNotFoundException {
        AdminDeleteUserResponseDto responseDto = adminService.deleteUser(userEmail);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
