package app.baeldung;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AclTests {
  private static Integer FIRST_MESSAGE_ID = 1;
  private static Integer SECOND_MESSAGE_ID = 2;
  private static Integer THIRD_MESSAGE_ID = 3;
  private static String EDITTED_CONTENT = "EDITED";

  @Autowired
  NoticeMessageRepository repo;

  @Test
  @WithMockUser(username = "manager")
  void givenUserManager_whenFindAllMessage_thenReturnFirstMessage() {
    var details = repo.findAll();
    assertNotNull(details);
    assertEquals(1, details.size());
  }

  @Test
  @WithMockUser(username = "manager")
  void givenUserManager_whenFind1stMessageByIdAndUpdateItsContent_thenOK() {
    var m1 = repo.findById(FIRST_MESSAGE_ID);
    assertNotNull(m1);
    assertEquals(FIRST_MESSAGE_ID, m1.getId());
    m1.setContent(EDITTED_CONTENT);
    repo.save(m1);

    var m2 = repo.findById(FIRST_MESSAGE_ID);
    assertNotNull(m2);
    assertEquals(FIRST_MESSAGE_ID, m2.getId());
    assertEquals(EDITTED_CONTENT, m2.getContent());

  }
}
