package app.forum;

import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.StreamSupport.stream;

@Value
public class ForumView {
  Long id;
  String name, description;

  public static ForumView from(Forum forum) {
    return new ForumView(forum.getId(), forum.getName(), forum.getDescription());
  }

  public static List<ForumView> from(Iterable<Forum> forums) {
    return stream(forums.spliterator(), false)
        .map(ForumView::from)
        .collect(toUnmodifiableList());
  }

}
