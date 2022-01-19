package com.pk.project_io.security.admin.group;

import com.pk.project_io.group.Group;
import com.pk.project_io.group.GroupService;
import com.pk.project_io.group.exceptions.GroupNotFoundException;
import com.pk.project_io.security.admin.AbstractService;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.group.dto.AdminGroupGetDto;
import com.pk.project_io.security.admin.group.dto.AdminGroupMapper;
import com.pk.project_io.security.admin.group.dto.AdminGroupPutDto;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminGroupService implements AbstractService<AdminGroupGetDto, AdminGroupPutDto, AdminActionResponseDto> {

    private final GroupService groupService;
    private final AdminGroupMapper mapper;

    public AdminGroupService(GroupService groupService, AdminGroupMapper mapper) {
        this.groupService = groupService;
        this.mapper = mapper;
    }

    @Override
    public List<AdminGroupGetDto> getAllByData(String name) throws Exception {
        return groupService.findRawGroupByName(name).stream()
                .map(mapper::groupToAdminGroupGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<AdminGroupGetDto> getAll() {
        return groupService.getAllRawUserGroups().stream()
                .map(mapper::groupToAdminGroupGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public AdminActionResponseDto editUserComponent(String userEmail, Long id, AdminGroupPutDto newComponent) throws Exception {
        groupService.updateUserGroup(userEmail, id, newComponent.getMode(), mapper.adminGroupPutDtoToGroupPutDto(newComponent));
        return new AdminActionResponseDto(String.format("Successfully updated %s to %s", newComponent.getMode(), newComponent.getNewValue()));
    }

    @Override
    public AdminActionResponseDto deleteUserComponent(String email, Long id) throws Exception {
        groupService.deleteGroup(email, id);
        return new AdminActionResponseDto("Group deleted!");
    }

    public AdminActionResponseDto addUserToGroup(Long groupId, String username) throws GroupNotFoundException, UserNotFoundException {
        Group group = groupService.getGroupById(groupId);
        groupService.addUserToGroup(group.getOwner().getEmail(), groupId, username);
        return new AdminActionResponseDto(String.format("User added to group id: %s!", username));
    }

    public AdminActionResponseDto removeUserFromGroup(Long groupId, String username) throws GroupNotFoundException, UserNotFoundException {
        Group group = groupService.getGroupById(groupId);
        groupService.addUserToGroup(group.getOwner().getEmail(), groupId, username);
        return new AdminActionResponseDto(String.format("User deleted from group id: %s!", username));
    }

}
