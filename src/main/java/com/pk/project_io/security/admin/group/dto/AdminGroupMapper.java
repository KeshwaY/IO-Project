package com.pk.project_io.security.admin.group.dto;

import com.pk.project_io.group.Group;
import com.pk.project_io.group.dto.GroupPutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface AdminGroupMapper {

    @Mapping(target = "ownerEmail", source = "")
    @Mapping(target = "usersEmails", source = "")
    AdminGroupGetDto groupToAdminGroupGetDto(Group group);

    GroupPutDto adminGroupPutDtoToGroupPutDto(AdminGroupPutDto adminGroupPutDto);

}
