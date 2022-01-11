package com.pk.project_io.post;

import com.pk.project_io.user.User;
import com.pk.project_io.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;
    private final UserRepository userRepository;

    public PostService(PostRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public void createPost(String userName, Post post) {
        User user = userRepository.findByUsername(userName).get();
        post.setUser(user);
        repository.save(post);
    }

    public List<Post> findUserPosts(String userName) {
        return repository.findByUser_Username(userName);
    }

}
