package com.pk.project_io.user.dto;

import com.pk.project_io.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "userGroup", ignore = true)
    @Mapping(target = "groupsOwnedBy", ignore = true)
    User userPostDtoToUser(UserPostDto userPostDto);

    UserGetDto userToUserGetDto(User user);

}
