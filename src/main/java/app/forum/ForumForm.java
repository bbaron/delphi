package app.forum;

import app.user.DelphiUser;
import lombok.Data;

@Data
public class ForumForm {
  private String name, description;

  public static ForumForm from(Forum forum) {
    var form = new ForumForm();
    form.setDescription(forum.getDescription());
    form.setName(forum.getName());
    return form;
  }

  public Forum toForum(DelphiUser user) {
    return new Forum(name, description, user);
  }
}
