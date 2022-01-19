package com.pk.project_io.security.admin.comment;

import com.pk.project_io.comment.exceptions.CommentNotFoundException;
import com.pk.project_io.security.admin.comment.dto.AdminCommentGetDto;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/comments")
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    public AdminCommentController(AdminCommentService adminCommentService) {
        this.adminCommentService = adminCommentService;
    }

    @GetMapping("/{user_email}")
    public ResponseEntity<List<AdminCommentGetDto>> getAllUserComments(
            @PathVariable("user_email") String userEmail
    ) throws UserNotFoundException {
        List<AdminCommentGetDto> adminCommentGetDto = adminCommentService.getUserComments(userEmail);
        return new ResponseEntity<>(adminCommentGetDto, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AdminCommentGetDto>> getAllComments() {
        List<AdminCommentGetDto> adminCommentGetDto = adminCommentService.getAllComments();
        return new ResponseEntity<>(adminCommentGetDto, HttpStatus.OK);
    }

    @PutMapping("/{user_email}")
    public ResponseEntity<AdminActionResponseDto> editUserComment(
            @PathVariable("user_email") String userEmail,
            @RequestParam("comment_id") Long postId,
            @RequestBody @Valid AdminPutDto adminPutDto
    ) throws UserNotFoundException, CommentNotFoundException {
        AdminActionResponseDto responseDto = adminCommentService.editUserComment(userEmail, postId, adminPutDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{user_email}")
    public ResponseEntity<AdminActionResponseDto> deleteUserComment(
            @PathVariable("user_email") String userEmail,
            @RequestParam("comment_id") Long postId
    ) throws UserNotFoundException, CommentNotFoundException {
        AdminActionResponseDto responseDto = adminCommentService.deleteUserComment(userEmail, postId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
