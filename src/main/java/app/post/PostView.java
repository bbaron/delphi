package app.post;

import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.StreamSupport.stream;

@Value
public class PostView {
  Long id;
  String title, body;

  public static PostView from(Post forum) {
    return new PostView(forum.getId(), forum.getTitle(), forum.getBody());
  }

  public static List<PostView> from(Iterable<Post> forums) {
    return stream(forums.spliterator(), false)
        .map(PostView::from)
        .collect(toUnmodifiableList());
  }

}
