package com.pk.project_io.comment.dto;

import com.pk.project_io.comment.Comment;
import com.pk.project_io.utils.mapper.DtoTranslator;
import com.pk.project_io.utils.mapper.PostToPostId;
import com.pk.project_io.utils.mapper.UserToUsername;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = DtoTranslator.class
)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "timeCreated", ignore = true)
    Comment commentPostDtoToComment(CommentPostDto commentPostDto);

    @Mapping(source = "user", target = "username", qualifiedBy = UserToUsername.class)
    @Mapping(source = "post", target = "postId", qualifiedBy = PostToPostId.class)
    CommentGetDto commentToCommentGetDto(Comment comment);

}
