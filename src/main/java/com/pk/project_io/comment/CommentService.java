package com.pk.project_io.comment;

import com.pk.project_io.post.Post;
import com.pk.project_io.post.PostRepository;
import com.pk.project_io.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void createComment(String userName, Comment comment) {
        Post post = postRepository.findByUser_Username(userName).get(0);
        User user = post.getUser();
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
    }

    public List<Comment> getUserComments(String userName) {
        return commentRepository.findByUser_Username(userName);
    }

}
