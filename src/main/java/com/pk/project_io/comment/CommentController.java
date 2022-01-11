package com.pk.project_io.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestParam(name = "user_name") String userName, @RequestBody Comment comment) {
        commentService.createComment(userName, comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/get/{user_name}")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable("user_name") String userName) {
        List<Comment> posts = commentService.getUserComments(userName);
        if (posts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


}
