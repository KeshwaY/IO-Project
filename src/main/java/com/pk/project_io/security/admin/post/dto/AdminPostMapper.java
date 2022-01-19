package com.pk.project_io.security.admin.post.dto;

import com.pk.project_io.post.Post;
import com.pk.project_io.post.dto.PostPutDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.utils.mapper.DtoTranslator;
import com.pk.project_io.utils.mapper.UserToUserEmail;
import com.pk.project_io.utils.mapper.UsersToUserEmails;
import com.pk.project_io.utils.mapper.UserToUsername;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = DtoTranslator.class
)
public interface AdminPostMapper {

    @Mapping(source = "user", target = "username", qualifiedBy = UserToUsername.class)
    @Mapping(source = "user", target = "userEmail", qualifiedBy = UserToUserEmail.class)
    AdminPostGetDto postToAdminPostGetDto(Post post);

    @Mapping(target = "newDescription", source = "newValue")
    PostPutDto convertAdminPutDtoToPostPutDto(AdminPutDto adminPutDto);

}
