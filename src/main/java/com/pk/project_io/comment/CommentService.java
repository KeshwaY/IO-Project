package com.pk.project_io.comment;

import com.pk.project_io.post.Post;
import com.pk.project_io.post.PostRepository;
import com.pk.project_io.user.User;
import com.pk.project_io.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void createComment(String userName, Comment comment) {
        Post post = postRepository.findByUserUsername(userName).get(0);
        User user = post.getUser();
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
    }

    public List<Comment> getUserComments(String userName) {
        return commentRepository.findByUserUsername(userName);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalStateException("No such post found"));
        return commentRepository.findByPost(post);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("No such user found"));
        return commentRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void editComment(Long commentId, Comment commentToUpdate) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("No such comment"));
        comment.setDescription(commentToUpdate.getDescription());
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("No such comment"));
        commentRepository.delete(comment);
    }

}
