package com.pk.project_io.utils.mapper;

import com.pk.project_io.post.Post;
import com.pk.project_io.user.User;
import org.springframework.stereotype.Component;

@Component
@BasicDtoTranslator
public class DtoTranslator {

    @PostToPostId
    public Long convertPostToPostId(Post post) {
        return post.getId();
    }

    @UserToUsername
    public String convertUserToUsername(User user) {
        return user.getUsername();
    }

    @UserToUserEmail
    public String convertUseToUserEmail(User user) {
        return user.getEmail();
    }

}
