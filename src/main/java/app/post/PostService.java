package app.post;

import app.forum.Forum;
import app.forum.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final ForumRepository forumRepository;

  public List<Post> getPostsByForumId(Long forumId) {
    var forum = forumRepository.getOne(forumId);
    return postRepository.findAllByForum(forum);
  }
}
