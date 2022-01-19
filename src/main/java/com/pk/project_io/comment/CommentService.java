package com.pk.project_io.comment;

import com.pk.project_io.comment.dto.*;
import com.pk.project_io.comment.exceptions.CommentNotFoundException;
import com.pk.project_io.post.Post;
import com.pk.project_io.post.PostService;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper mapper;
    private final PostService postService;
    private final UserService userService;

    public CommentService(
            CommentRepository commentRepository,
            CommentMapper commentMapper,
            PostService postService,
            UserService userService
    ) {
        this.commentRepository = commentRepository;
        this.mapper = commentMapper;
        this.postService = postService;
        this.userService = userService;
    }

    public CommentGetDto createComment(String userEmail, Long postId, CommentPostDto commentPostDto) throws PostNotFoundException, UserNotFoundException {
        Post post = postService.findRawPostById(postId);
        User user = userService.getRawUserByEmail(userEmail);
        Comment comment = mapper.commentPostDtoToComment(commentPostDto);
        comment.setUser(user);
        comment.setPost(post);
        comment.setTimeCreated(LocalDateTime.now());
        commentRepository.save(comment);
        return mapper.commentToCommentGetDto(comment);
    }

    public List<CommentGetDto> getUserComments(String userEmail) throws UserNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        List<Comment> comments = commentRepository.findByUser(user);
        return comments.stream()
                .map(mapper::commentToCommentGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public CommentGetDto editComment(String userEmail, Long commentId, CommentUpdateDto commentToUpdate) throws CommentNotFoundException, UserNotFoundException {
        Comment comment = getCommentAndCheckIfBelongsToUser(userEmail, commentId);
        comment.setDescription(commentToUpdate.getNewDescription());
        commentRepository.save(comment);
        return mapper.commentToCommentGetDto(comment);
    }

    public CommentActionResponseDto deleteComment(String userEmail, Long commentId) throws UserNotFoundException, CommentNotFoundException {
        Comment comment = getCommentAndCheckIfBelongsToUser(userEmail, commentId);
        comment.getPost().getComments().remove(comment);
        commentRepository.delete(comment);
        return new CommentActionResponseDto("Comment deleted!");
    }

    private Comment getCommentAndCheckIfBelongsToUser(String userEmail, Long commentId) throws UserNotFoundException, CommentNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        Comment comment = findById(commentId);
        if (!comment.getUser().equals(user)) {
            throw new CommentNotFoundException();
        }
        return comment;
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) throws CommentNotFoundException {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<CommentGetDto> getAllCommentsByPost(Long postId) throws PostNotFoundException {
        Post post = postService.findRawPostById(postId);
        return commentRepository.findByPost(post).stream()
                .map(mapper::commentToCommentGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByUserEmail(String userEmail) throws UserNotFoundException {
        User user = userService.getRawUserByEmail(userEmail);
        return commentRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllRawComments() {
        return commentRepository.findAll();
    }

}
