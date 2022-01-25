package com.pk.project_io.group;

import com.pk.project_io.group.dto.GroupGetDto;
import com.pk.project_io.group.dto.GroupMapper;
import com.pk.project_io.group.dto.GroupPostDto;
import com.pk.project_io.group.dto.GroupPutDto;
import com.pk.project_io.group.exceptions.GroupNotFoundException;
import com.pk.project_io.user.User;
import com.pk.project_io.user.UserService;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper mapper;
    private final UserService userService;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, UserService userService) {
        this.groupRepository = groupRepository;
        this.mapper = groupMapper;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<GroupGetDto> getAllUserGroups() {
        return groupRepository.findAll().stream()
                .map(mapper::groupToGroupGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<GroupGetDto> getUserGroupByName(String userGroupName) {
        List<Group> group = findRawGroupByName(userGroupName);
        return group.stream()
                .map(mapper::groupToGroupGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public GroupGetDto addUserGroup(String userEmail, GroupPostDto groupPostDto) throws UserNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        Group group = mapper.groupPostDtoToGroup(groupPostDto);
        group.setOwner(user);
        group.setTimeCreated(LocalDateTime.now());
        group.setUsers(new HashSet<>());
        groupRepository.save(group);
        return mapper.groupToGroupGetDto(group);
    }

    public GroupGetDto addUserToGroup(String userEmail, Long groupId, String username) throws UserNotFoundException, GroupNotFoundException {
        Group group = getGroupAndCheckIfBelongsToUser(userEmail, groupId);
        User user = userService.getRawUserByUsername(username);
        group.getUsers().add(user);
        user.getUserGroup().add(group);
        groupRepository.save(group);
        return mapper.groupToGroupGetDto(group);
    }

    public GroupGetDto removeUserFromGroup(String userEmail, Long groupId, String username) throws UserNotFoundException, GroupNotFoundException {
        Group group = getGroupAndCheckIfBelongsToUser(userEmail, groupId);
        User user = userService.getRawUserByUsername(username);
        group.getUsers().remove(user);
        user.getUserGroup().remove(group);
        groupRepository.save(group);
        return mapper.groupToGroupGetDto(group);
    }

    public GroupGetDto updateUserGroup(String userEmail, Long groupId, String mode, GroupPutDto groupPutDto) throws UserNotFoundException, GroupNotFoundException {
        Group group = getGroupAndCheckIfBelongsToUser(userEmail, groupId);
        switch (mode) {
            case "name":
                group.setName(groupPutDto.getNewValue());
                break;
            case "description":
                group.setDescription(groupPutDto.getNewValue());
                break;
            case "owner":
                User user = userService.getRawUserByEmail(groupPutDto.getNewValue());
                group.setOwner(user);
                break;
        }
        groupRepository.save(group);
        return mapper.groupToGroupGetDto(group);
    }

    public void deleteGroup(String userEmail, Long id) throws UserNotFoundException, GroupNotFoundException {
        Group group = getGroupAndCheckIfBelongsToUser(userEmail, id);
        group.getUsers().forEach(u -> u.getUserGroup().remove(group));
        groupRepository.delete(group);
    }

    private Group getGroupAndCheckIfBelongsToUser(String userEmail, Long groupId) throws UserNotFoundException, GroupNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        Group group = findById(groupId);
        if (!group.getOwner().equals(user)) {
            throw new GroupNotFoundException();
        }
        return group;
    }

    @Transactional(readOnly = true)
    public Group findById(Long id) throws GroupNotFoundException {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public List<Group> findRawGroupByName(String groupName) {
        return groupRepository.findUserGroupByName(groupName);
    }

    public List<Group> getAllRawUserGroups() {
        return groupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Group getGroupById(Long id) throws GroupNotFoundException {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

}
