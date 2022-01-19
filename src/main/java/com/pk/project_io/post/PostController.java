package com.pk.project_io.post;

import com.pk.project_io.post.dto.PostActionResponseDto;
import com.pk.project_io.post.dto.PostGetDto;
import com.pk.project_io.post.dto.PostPostDto;
import com.pk.project_io.post.dto.PostPutDto;
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
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    public ResponseEntity<PostGetDto> createPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid PostPostDto postDto
    ) throws UserNotFoundException {
        PostGetDto postGetDto = postService.createPost(userDetails.getUsername(), postDto);
        return new ResponseEntity<>(postGetDto, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PostGetDto>> getUserPostsByUsername(
            @PathVariable String username
    ) {
        List<PostGetDto> posts = postService.findPostsByUserName(username);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PostActionResponseDto> editPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("post_id") Long id,
            @RequestBody PostPutDto postPutDto
    ) throws UserNotFoundException, PostNotFoundException {
        PostActionResponseDto responseDto = postService.editUserPost(userDetails.getUsername(), id, postPutDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PostActionResponseDto> deletePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("post_id") Long id
    ) throws UserNotFoundException, PostNotFoundException {
        PostActionResponseDto responseDto = postService.deleteUserPost(userDetails.getUsername(), id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
