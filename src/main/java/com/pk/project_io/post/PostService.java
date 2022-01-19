package com.pk.project_io.post;

import com.pk.project_io.post.dto.*;
import com.pk.project_io.post.exceptions.PostNotFoundException;
import com.pk.project_io.user.User;
import com.pk.project_io.user.UserService;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostMapper mapper;

    public PostService(PostRepository postRepository, UserService userService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.mapper = postMapper;
    }

    public PostGetDto createPost(String userEmail, PostPostDto postDto) throws UserNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        Post post = mapper.postPostDtoToPost(postDto);
        post.setUser(user);
        post.setTimeCreated(LocalDateTime.now());
        postRepository.save(post);
        return mapper.postToPostGetDto(post);
    }

    public PostActionResponseDto editUserPost(String userEmail, Long id, PostPutDto postPutDto) throws UserNotFoundException, PostNotFoundException {
        Post post = getPostAndCheckIfBelongsToUser(userEmail, id);
        post.setDescription(postPutDto.getNewDescription());
        postRepository.save(post);
        return new PostActionResponseDto(
                String.format("Successfully changed description to %s", postPutDto.getNewDescription())
        );
    }

    public PostActionResponseDto deleteUserPost(String userEmail, Long id) throws UserNotFoundException, PostNotFoundException {
        Post post = getPostAndCheckIfBelongsToUser(userEmail, id);
        postRepository.delete(post);
        return new PostActionResponseDto("Post deleted!");
    }

    private Post getPostAndCheckIfBelongsToUser(String userEmail, Long postId) throws UserNotFoundException, PostNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        Post post = findRawPostById(postId);
        if (!post.getUser().equals(user)) {
            throw new PostNotFoundException();
        }
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PostGetDto> findPostsByUserName(String userName) {
        return postRepository.findByUserUsername(userName).stream()
                .map(mapper::postToPostGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public Post findRawPostById(Long id) throws PostNotFoundException {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public List<Post> findRawPostByUser(User user) {
        return postRepository.findByUser(user);
    }

}
