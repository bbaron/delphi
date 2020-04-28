package app.post;

import app.forum.Forum;
import app.user.DelphiUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@EqualsAndHashCode(of = {"title", "author"})
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(
    name = "uniq_post__title_forum_id",
    columnNames = {"title", "forum_id"}))
@ToString(of = {"id", "title"})
public class Post {
  @Id
  @GeneratedValue
  private Long id;
  @NotBlank
  private String title;

  @NotBlank
  private String body;

  @ManyToOne(optional = false, fetch = LAZY)
  private DelphiUser author;

  @ManyToOne(optional = false, fetch = LAZY)
  private Forum forum;

  private LocalDateTime createdOn = now();

  private LocalDateTime updatedOn = now();

  public Post(String title, String body, DelphiUser author, Forum forum) {
    this.title = title;
    this.body = body;
    this.author = author;
    this.forum = forum;
  }
}
