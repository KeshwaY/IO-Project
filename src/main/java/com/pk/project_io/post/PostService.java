package com.pk.project_io.post;

import com.pk.project_io.user.User;
import com.pk.project_io.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void createPost(String userName, Post post) {
        User user = userRepository.findByUsername(userName).get();
        post.setUser(user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPostsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("No such user found"));
        return postRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Post> findPostsByUserName(String userName) {
        return postRepository.findByUserUsername(userName);
    }

    public void editPost(Long postId, Post postToUpdate) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("No such post found"));
        post.setDescription(postToUpdate.getDescription());
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("No such post found"));
        postRepository.delete(post);
    }
}
