package com.pk.project_io.security.admin.post;

import com.pk.project_io.post.Post;
import com.pk.project_io.post.PostService;
import com.pk.project_io.security.admin.AbstractService;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.security.admin.post.dto.AdminPostGetDto;
import com.pk.project_io.security.admin.post.dto.AdminPostMapper;
import com.pk.project_io.user.User;
import com.pk.project_io.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminPostService implements AbstractService<AdminPostGetDto, AdminPutDto, AdminActionResponseDto> {

    private final UserService userService;
    private final PostService postService;
    private final AdminPostMapper mapper;

    public AdminPostService(UserService userService, PostService postService, AdminPostMapper adminPostMapper) {
        this.userService = userService;
        this.postService = postService;
        this.mapper = adminPostMapper;
    }

    @Override
    public List<AdminPostGetDto> getAllByData(String userEmail) throws Exception {
        User user = userService.getRawUserByEmail(userEmail);
        List<Post> comments = postService.findRawPostByUser(user);
        return comments.stream()
                .map(mapper::postToAdminPostGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<AdminPostGetDto> getAll() {
        List<Post> comments = postService.getAll();
        return comments.stream()
                .map(mapper::postToAdminPostGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public AdminActionResponseDto editUserComponent(String userEmail, Long id, AdminPutDto newComponent) throws Exception {
        postService.editUserPost(userEmail, id, mapper.convertAdminPutDtoToPostPutDto(newComponent));
        return new AdminActionResponseDto(
                String.format("Successfully changed description of the post with id: %d to %s", id, newComponent.getNewValue())
        );
    }

    @Override
    public AdminActionResponseDto deleteUserComponent(String email, Long id) throws Exception {
        postService.deleteUserPost(email, id);
        return new AdminActionResponseDto(
                String.format("Successfully deleted comment with id: %d", id)
        );
    }
}
