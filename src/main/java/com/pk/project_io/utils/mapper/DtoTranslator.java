package com.pk.project_io.utils.mapper;

import com.pk.project_io.post.Post;
import com.pk.project_io.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @UsersToUserEmails
    public List<String> convertUsersToUserEmails(Set<User> userSet) {
        return userSet.stream()
                .map(User::getEmail)
                .collect(Collectors.toUnmodifiableList());
    }

}
