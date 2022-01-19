package com.pk.project_io.post.dto;

import com.pk.project_io.post.Post;
import com.pk.project_io.utils.mapper.DtoTranslator;
import com.pk.project_io.utils.mapper.UserToUsername;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = DtoTranslator.class
)
public interface PostMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "timeCreated", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Post postPostDtoToPost(PostPostDto postPostDto);

    @Mapping(source = "user", target = "username", qualifiedBy = UserToUsername.class)
    PostGetDto postToPostGetDto(Post post);

}
