package com.pk.project_io.group.dto;

import com.pk.project_io.group.Group;
import com.pk.project_io.user.User;
import com.pk.project_io.utils.mapper.DtoTranslator;
import com.pk.project_io.utils.mapper.UserToUsername;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = DtoTranslator.class
)
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "timeCreated", ignore = true)
    Group groupPostDtoToGroup(GroupPostDto groupPostDto);

    @Mapping(target = "ownerUsername", source = "owner", qualifiedBy = UserToUsername.class)
    @Mapping(source = "users", target = "users", qualifiedByName = "usersToString")
    GroupGetDto groupToGroupGetDto(Group group);

    @Named("usersToString")
    default Set<String> usersToString(Set<User> userSet) {
        return userSet.stream()
                .map(User::getUsername)
                .collect(Collectors.toUnmodifiableSet());
    }

}
