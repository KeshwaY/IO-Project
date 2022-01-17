package com.pk.project_io.post;

import com.pk.project_io.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserUsername(String userName);

    List<Post> findByUser(User user);
}
