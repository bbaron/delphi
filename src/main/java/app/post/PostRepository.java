package app.post;

import app.forum.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  boolean existsByTitleAndForum(String name, Forum forum);

  List<Post> findAllByForum(Forum forum);
}
