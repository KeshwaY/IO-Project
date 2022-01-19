package com.pk.project_io.security.admin.group;

import com.pk.project_io.group.GroupService;
import com.pk.project_io.group.dto.GroupGetDto;
import com.pk.project_io.security.admin.AbstractService;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.security.admin.group.dto.AdminGroupGetDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminGroupService implements AbstractService<AdminGroupGetDto, AdminPutDto, AdminActionResponseDto> {

    private final GroupService groupService;

    public AdminGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public List<AdminGroupGetDto> getAllByData(String userEmail) throws Exception {
        return null;
    }

    @Override
    public List<AdminGroupGetDto> getAll() {
        return null;
    }

    @Override
    public AdminActionResponseDto editUserComponent(String userEmail, Long id, AdminPutDto newComponent) throws Exception {
        return null;
    }

    @Override
    public AdminActionResponseDto deleteUserComponent(String email, Long id) throws Exception {
        return null;
    }

    public AdminGroupGetDto addUserToGroup(String username, Long groupId, String username1) {
        return null;
    }

    public AdminGroupGetDto removeUserFromGroup(String username, Long groupId, String username1) {
        return null;
    }
}
