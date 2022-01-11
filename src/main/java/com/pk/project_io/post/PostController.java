package com.pk.project_io.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestParam(name = "user_name") String userName, @RequestBody Post post) {
        service.createPost(userName, post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/get/{user_name}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable("user_name") String userName) {
        List<Post> posts = service.findUserPosts(userName);
        if (posts.size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
