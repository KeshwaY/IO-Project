package com.pk.project_io.security.admin.group;

import com.pk.project_io.group.exceptions.GroupNotFoundException;
import com.pk.project_io.security.admin.AbstractRestController;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.security.admin.group.dto.AdminGroupGetDto;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/admin/groups")
public class AdminGroupController extends AbstractRestController<AdminGroupService, AdminGroupGetDto, AdminPutDto, AdminActionResponseDto> {

    public AdminGroupController(AdminGroupService service) {
        super(service);
    }

    @PostMapping(value = "/{group_id}/add-user")
    public ResponseEntity<AdminGroupGetDto> addUserToGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("group_id") Long groupId,
            @RequestParam("username") String username
    ) throws UserNotFoundException, GroupNotFoundException {
        AdminGroupGetDto group = service.addUserToGroup(userDetails.getUsername(), groupId, username);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping(value = "/{group_id}/remove-user")
    public ResponseEntity<AdminGroupGetDto> removeUserFromGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("group_id") Long groupId,
            @RequestParam("username") String username
    ) throws UserNotFoundException, GroupNotFoundException {
        AdminGroupGetDto group = service.removeUserFromGroup(userDetails.getUsername(), groupId, username);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

}
