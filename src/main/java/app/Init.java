package app;

import app.forum.Forum;
import app.forum.ForumRepository;
import app.user.DelphiUser;
import app.user.DelphiUserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Init implements ApplicationListener<ContextRefreshedEvent> {
  private final DelphiUserRepository delphiUserRepository;
  private final ForumRepository forumRepository;
  boolean alreadySetup;

  @Override
//  @Transactional(noRollbackFor = {DataIntegrityViolationException.class, DataIntegrityViolationException.class})
  public void onApplicationEvent(ContextRefreshedEvent event) {
    Faker faker = new Faker();
    if (alreadySetup) return;
    var user1 = new DelphiUser("user1@example.com", "user1");
    var user2 = new DelphiUser("user2@example.com", "user2");
    var root = new DelphiUser("root@example.com", "root");
    var users = List.of(user1, user2, root);
    delphiUserRepository.saveAll(users).forEach(System.out::println);
    alreadySetup = true;

    Calendar cal = Calendar.getInstance();
    Date to = cal.getTime();
    cal.add(Calendar.MONTH, -6);
    Date from = cal.getTime();
    for (int i = 0; i < 30; i++) {
      String name = faker.beer().name();
      String description = faker.lorem().sentence(5);

      Date d = faker.date().between(from, to);
      var ldt = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
      var user = i % 2 == 0 ? user1 : user2;
      var forum = new Forum(name, description, user);
      forum.setCreatedOn(ldt);
      forum.setUpdatedOn(ldt);
      if (forumRepository.existsByName(name)) {
        name = String.format("%s_%s", name, i);
        forum.setName(name);
      }
      System.out.printf("save 1 %s%n", forum);
      forumRepository.saveAndFlush(forum);
      System.out.println(forum);
    }
  }
}
