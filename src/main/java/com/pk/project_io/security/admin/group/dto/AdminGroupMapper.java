package com.pk.project_io.security.admin.group.dto;

import com.pk.project_io.group.Group;
import com.pk.project_io.group.dto.GroupPutDto;
import com.pk.project_io.utils.mapper.DtoTranslator;
import com.pk.project_io.utils.mapper.UserToUserEmail;
import com.pk.project_io.utils.mapper.UsersToUserEmails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = DtoTranslator.class
)
public interface AdminGroupMapper {

    @Mapping(target = "ownerEmail", source = "owner", qualifiedBy = UserToUserEmail.class)
    @Mapping(target = "usersEmails", source = "users", qualifiedBy = UsersToUserEmails.class)
    AdminGroupGetDto groupToAdminGroupGetDto(Group group);

    GroupPutDto adminGroupPutDtoToGroupPutDto(AdminGroupPutDto adminGroupPutDto);

}
