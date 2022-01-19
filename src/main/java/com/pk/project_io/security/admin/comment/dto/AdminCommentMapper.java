package com.pk.project_io.security.admin.comment.dto;

import com.pk.project_io.comment.Comment;
import com.pk.project_io.comment.dto.CommentUpdateDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.utils.mapper.DtoTranslator;
import com.pk.project_io.utils.mapper.PostToPostId;
import com.pk.project_io.utils.mapper.UserToUserEmail;
import com.pk.project_io.utils.mapper.UsersToUserEmails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = DtoTranslator.class
)
public interface AdminCommentMapper {

    @Mapping(source = "user", target = "userEmail", qualifiedBy = UserToUserEmail.class)
    @Mapping(source = "post", target = "postId", qualifiedBy = PostToPostId.class)
    AdminCommentGetDto convertCommentToAdminCommentGetDto(Comment comment);


    @Mapping(target = "newDescription", source = "newValue")
    CommentUpdateDto convertAdminPutDtoToCommentUpdateDto(AdminPutDto adminPutDto);

}
