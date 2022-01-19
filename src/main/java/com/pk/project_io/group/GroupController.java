package com.pk.project_io.group;

import com.pk.project_io.group.dto.GroupGetDto;
import com.pk.project_io.group.dto.GroupPostDto;
import com.pk.project_io.group.dto.GroupPutDto;
import com.pk.project_io.group.exceptions.GroupNotFoundException;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupGetDto>> getAllUserGroups() {
        List<GroupGetDto> groups = groupService.getAllUserGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{group_name}")
    public ResponseEntity<List<GroupGetDto>> getUserGroupByName(
            @PathVariable("group_name") String groupName
    ) throws GroupNotFoundException {
        List<GroupGetDto> group = groupService.getUserGroupByName(groupName);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<GroupGetDto> addUserGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid GroupPostDto groupPostDto
    ) throws UserNotFoundException {
        GroupGetDto group = groupService.addUserGroup(userDetails.getUsername(), groupPostDto);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{group_id}/add-user")
    public ResponseEntity<GroupGetDto> addUserToGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("group_id") Long groupId,
            @RequestParam("username") String username
    ) throws UserNotFoundException, GroupNotFoundException {
        GroupGetDto group = groupService.addUserToGroup(userDetails.getUsername(), groupId, username);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{group_id}/remove-user")
    public ResponseEntity<GroupGetDto> removeUserFromGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("group_id") Long groupId,
            @RequestParam("username") String username
    ) throws UserNotFoundException, GroupNotFoundException {
        GroupGetDto group = groupService.removeUserFromGroup(userDetails.getUsername(), groupId, username);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PutMapping("/{group_id}")
    public ResponseEntity<GroupGetDto> updateUserGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("group_id") Long groupId,
            @RequestParam String mode,
            @RequestBody @Valid GroupPutDto groupPutDto
    ) throws UserNotFoundException, GroupNotFoundException {
        GroupGetDto group = groupService.updateUserGroup(userDetails.getUsername(), groupId, mode, groupPutDto);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping("/{group_id}")
    public ResponseEntity<Void> deleteUserGroup(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("group_id") Long groupId
    ) throws UserNotFoundException, GroupNotFoundException {
        groupService.deleteGroup(userDetails.getUsername(), groupId);
        return ResponseEntity.ok().build();
    }

}
