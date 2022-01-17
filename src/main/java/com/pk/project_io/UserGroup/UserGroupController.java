package com.pk.project_io.UserGroup;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class UserGroupController {

    private UserGroupService userGroupService;

    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @GetMapping
    public ResponseEntity<List<UserGroup>> getAllUserGroups() {
        List<UserGroup> groups = userGroupService.getAllUserGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/by-name")
    public ResponseEntity<UserGroup> getUserGroupByName(@RequestParam String groupName) {
        UserGroup group = userGroupService.getUserGroupByName(groupName);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addUserGroup(@RequestBody UserGroup userGroup) {
        userGroupService.addUserGroup(userGroup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserGroup(@PathVariable Long id, @RequestBody UserGroup userGroupToUpdate) {
        userGroupService.updateUserGroup(id, userGroupToUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable Long id) {
        userGroupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

}
