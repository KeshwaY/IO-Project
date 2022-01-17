package com.pk.project_io.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Comment> createComment(@RequestParam String username, @RequestBody Comment comment) {
        commentService.createComment(username, comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable String username) {
        List<Comment> comments = commentService.getUserComments(username);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<List<Comment>> getUserCommentsByPost(@PathVariable Long id) {
        List<Comment> comments = commentService.getAllCommentsByPost(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Comment>> getUserCommentsByUser(@PathVariable Long id) {
        List<Comment> comments = commentService.getAllCommentsByUser(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editComment(@PathVariable Long id, @RequestBody Comment commentToUpdate) {
        commentService.editComment(id, commentToUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
