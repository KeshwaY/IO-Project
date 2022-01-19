package com.pk.project_io.comment;

import com.pk.project_io.comment.dto.CommentActionResponseDto;
import com.pk.project_io.comment.dto.CommentGetDto;
import com.pk.project_io.comment.dto.CommentPostDto;
import com.pk.project_io.comment.dto.CommentUpdateDto;
import com.pk.project_io.comment.exceptions.CommentNotFoundException;
import com.pk.project_io.post.exceptions.PostNotFoundException;
import com.pk.project_io.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<CommentGetDto> createComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("post_id") Long postId,
            @RequestBody @Valid CommentPostDto commentPostDto
    ) throws PostNotFoundException, UserNotFoundException {
        CommentGetDto comment = commentService.createComment(userDetails.getUsername(), postId, commentPostDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentGetDto>> getUserComments(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws UserNotFoundException {
        List<CommentGetDto> comments = commentService.getUserComments(userDetails.getUsername());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CommentGetDto> editComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("comment_id") Long id,
            @RequestBody @Valid CommentUpdateDto commentToUpdate
    ) throws CommentNotFoundException, UserNotFoundException {
        CommentGetDto responseDto = commentService.editComment(userDetails.getUsername(), id, commentToUpdate);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentActionResponseDto> deleteComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) throws UserNotFoundException, CommentNotFoundException {
        CommentActionResponseDto responseDto = commentService.deleteComment(userDetails.getUsername(), id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<List<CommentGetDto>> getCommentsByPost(
            @PathVariable Long id
    ) throws PostNotFoundException {
        List<CommentGetDto> comments = commentService.getAllCommentsByPost(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
