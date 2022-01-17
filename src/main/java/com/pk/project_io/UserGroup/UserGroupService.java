package com.pk.project_io.UserGroup;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserGroupService {

    private UserGroupRepository userGroupRepository;

    public UserGroupService(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Transactional(readOnly = true)
    public List<UserGroup> getAllUserGroups() {
        return userGroupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserGroup getUserGroupByName(String userGroupName) {
        return userGroupRepository.findUserGroupByName(userGroupName);
    }

    public void addUserGroup(UserGroup userGroup) {
            userGroupRepository.save(userGroup);
    }

    public void updateUserGroup(Long groupId, UserGroup groupToUpdate) {
        UserGroup group = userGroupRepository.findById(groupId).orElseThrow(() -> new IllegalStateException("Group already exists"));
        group.setName(groupToUpdate.getName());
        group.setDescription(groupToUpdate.getDescription());
        userGroupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        UserGroup group = userGroupRepository.findById(id).orElseThrow(() -> new IllegalStateException("Group already exists"));
        userGroupRepository.delete(group);
    }

}
