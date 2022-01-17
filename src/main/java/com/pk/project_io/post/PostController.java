package com.pk.project_io.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    public ResponseEntity<Post> createPost(@RequestParam(name = "username") String username, @RequestBody Post post) {
        postService.createPost(username, post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Post>> getUserPostsByUsername(@PathVariable String username) {
        List<Post> posts = postService.findPostsByUserName(username);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/all-by-user/{id}")
    public ResponseEntity<List<Post>> getAllPostsByUser(@PathVariable Long id) {
        List<Post> posts = postService.getAllPostsByUser(id);
        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editPost(@PathVariable Long id, @RequestBody Post postToUpdate) {
        postService.editPost(id, postToUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
