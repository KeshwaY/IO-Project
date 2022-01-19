package com.pk.project_io.security.admin.group;

import com.pk.project_io.group.exceptions.GroupNotFoundException;
import com.pk.project_io.security.admin.AbstractRestController;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.group.dto.AdminGroupGetDto;
import com.pk.project_io.security.admin.group.dto.AdminGroupPutDto;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/admin/groups")
public class AdminGroupController extends AbstractRestController<AdminGroupService, AdminGroupGetDto, AdminGroupPutDto, AdminActionResponseDto> {

    public AdminGroupController(AdminGroupService service) {
        super(service);
    }

    @PostMapping(value = "/{group_id}/add-user")
    public ResponseEntity<AdminActionResponseDto> addUserToGroup(
            @PathVariable("group_id") Long groupId,
            @RequestParam("username") String username
    ) throws UserNotFoundException, GroupNotFoundException {
        AdminActionResponseDto group = service.addUserToGroup(groupId, username);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping(value = "/{group_id}/remove-user")
    public ResponseEntity<AdminActionResponseDto> removeUserFromGroup(
            @PathVariable("group_id") Long groupId,
            @RequestParam("username") String username
    ) throws UserNotFoundException, GroupNotFoundException {
        AdminActionResponseDto group = service.removeUserFromGroup(groupId, username);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

}
