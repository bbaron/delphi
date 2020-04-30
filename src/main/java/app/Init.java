package app;

import app.forum.Forum;
import app.forum.ForumRepository;
import app.post.Post;
import app.post.PostRepository;
import app.user.DelphiUser;
import app.user.DelphiUserRepository;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class Init implements ApplicationListener<ContextRefreshedEvent> {
  private final DelphiUserRepository delphiUserRepository;
  private final ForumRepository forumRepository;
  private final PostRepository postRepository;
  private final Faker faker = new Faker();
  private final Date from, to;
  private final List<DelphiUser> users = new ArrayList<>();
  private final Random random = new Random();
  boolean alreadySetup;

  public Init(DelphiUserRepository delphiUserRepository, ForumRepository forumRepository, PostRepository postRepository) {
    this.delphiUserRepository = delphiUserRepository;
    this.forumRepository = forumRepository;
    this.postRepository = postRepository;
    Calendar cal = Calendar.getInstance();
    to = cal.getTime();
    cal.add(Calendar.MONTH, -6);
    from = cal.getTime();
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (alreadySetup) return;
    try {
      setup();
    } finally {
      alreadySetup = true;
    }
  }

  private void setup() {
    for (int i = 1; i <= 5; i++) {
      var user = "user" + i;
      users.add(new DelphiUser(String.format("%s@example.com", user), user));
    }
    var root = DelphiUser.administrator("root@example.com", "root");
    delphiUserRepository.saveAndFlush(root);
    delphiUserRepository.saveAll(users);
    alreadySetup = true;

    for (int i = 0; i < 30; i++) {
      String name = faker.beer().name();
      String description = faker.lorem().sentence(5);

      var founder = randomUser();
      var forum = new Forum(name, description, founder);
      var forumDate = randomDateAfter(from);
      var forumLdt = date2ldt(forumDate);
      forum.setCreatedOn(forumLdt);
      forum.setUpdatedOn(forumLdt);
      if (forumRepository.existsByName(name)) {
        name = String.format("%s_%s", name, i);
        forum.setName(name);
      }
      forumRepository.saveAndFlush(forum);
      log.trace(forum);
      for (int j = 0; j < 20; j++) {
        var title = faker.book().title();
        var body = faker.chuckNorris().fact();
        var postDate = randomDateAfter(forumDate);
        var postLdt = date2ldt(postDate);
        var author = randomUser();
        var post = new Post(title, body, author, forum);
        post.setCreatedOn(postLdt);
        post.setUpdatedOn(postLdt);
        log.info("{}, {}", forum, post);
        if (postRepository.existsByTitleAndForum(title, forum)) {
          log.info("{} exists in {}", post, forum);
        } else {
          postRepository.saveAndFlush(post);
          log.info("saved {}", post);
        }
      }
    }

  }

  private Date randomDateAfter(Date min) {
    return faker.date().between(min, to);
  }

  private LocalDateTime date2ldt(Date d) {
    return LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
  }

  private DelphiUser randomUser() {
    return users.get(random.nextInt(users.size()));
  }
}
