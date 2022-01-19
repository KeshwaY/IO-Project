package com.pk.project_io.security.admin.comment;

import com.pk.project_io.comment.Comment;
import com.pk.project_io.comment.CommentService;
import com.pk.project_io.comment.exceptions.CommentNotFoundException;
import com.pk.project_io.post.PostService;
import com.pk.project_io.security.admin.comment.dto.AdminCommentGetDto;
import com.pk.project_io.security.admin.comment.dto.AdminCommentMapper;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminCommentService {

    private final CommentService commentService;
    private final AdminCommentMapper mapper;

    public AdminCommentService(CommentService commentService, AdminCommentMapper adminCommentMapper) {
        this.commentService = commentService;
        this.mapper = adminCommentMapper;
    }

    public List<AdminCommentGetDto> getAllComments() {
        return commentService.getAllRawComments().stream()
                .map(mapper::convertCommentToAdminCommentGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<AdminCommentGetDto> getUserComments(String userEmail) throws UserNotFoundException {
        List<Comment> commentList = commentService.getAllCommentsByUserEmail(userEmail);
        return commentList.stream()
                .map(mapper::convertCommentToAdminCommentGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public AdminActionResponseDto editUserComment(String userEmail, Long commentId, AdminPutDto adminPutDto) throws UserNotFoundException, CommentNotFoundException {
        commentService.editComment(userEmail, commentId, mapper.convertAdminPutDtoToCommentUpdateDto(adminPutDto));
        return new AdminActionResponseDto(
                String.format("Successfully changed description of the comment with id: %d to %s", commentId, adminPutDto.getNewValue())
        );
    }

    public AdminActionResponseDto deleteUserComment(String userEmail, Long commentId) throws UserNotFoundException, CommentNotFoundException {
        commentService.deleteComment(userEmail, commentId);
        return new AdminActionResponseDto(
                String.format("Successfully deleted comment with id: %d", commentId)
        );
    }

}
