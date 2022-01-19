package com.pk.project_io.comment;

import com.pk.project_io.post.Post;
import com.pk.project_io.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUserUsername(String userName);

    List<Comment> findByUser(User user);

    List<Comment> findByPost(Post post);

}
