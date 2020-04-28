package app.forum;

import app.user.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ForumService {
  private final ForumRepository forumRepository;
  private final UserContext userContext;

  private void createForum(ForumForm forumForm) {
    var founder = userContext.getCurrentUser().orElseThrow(() -> new IllegalStateException("No logged in user"));
    var forum = forumForm.toForum(founder);
    forumRepository.save(forum);
  }

  public Iterable<Forum> getForums() {
    return forumRepository.findAll();
  }
}
