package com.pk.project_io.user;

import com.pk.project_io.security.roles.exceptions.ServerRoleNotFound;
import com.pk.project_io.user.dto.UpdatePropertyPostDto;
import com.pk.project_io.user.dto.UpdateResponseDto;
import com.pk.project_io.user.dto.UserGetDto;
import com.pk.project_io.user.dto.UserPostDto;
import com.pk.project_io.user.exceptions.UserAlreadyExistsException;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import com.pk.project_io.user.exceptions.UserPropertyUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserGetDto> createUser(
            @RequestBody @Valid UserPostDto userPostDto
    ) throws ServerRoleNotFound, UserAlreadyExistsException {
        UserGetDto user = userService.createUser(userPostDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateResponseDto> updateUserProperty(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("property") String property,
            @RequestBody @Valid UpdatePropertyPostDto updatePropertyPostDto
    ) throws UserNotFoundException, UserPropertyUpdateException {
        UpdateResponseDto updateResponseDto = userService.updateUserProperty(userDetails.getUsername(), property, updatePropertyPostDto);
        return new ResponseEntity<>(updateResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<UserGetDto> getUserData(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws UserNotFoundException {
        UserGetDto userGetDto = userService.getReadOnlyUserByEmail(userDetails.getUsername());
        return new ResponseEntity<>(userGetDto, HttpStatus.OK);
    }

}
