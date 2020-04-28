package app.forum;

import app.user.DelphiUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
public class Forum {
  @Id
  @GeneratedValue
  private Long id;
  @NotBlank
  @Column(unique = true)
  private String name;

  @NotBlank
  private String description;

  @ManyToOne(optional = false, fetch = LAZY)
  private DelphiUser founder;

  private Boolean enabled = true;
  private LocalDateTime createdOn = now();
  private LocalDateTime updatedOn = now();

  public Forum(String name, String description, DelphiUser founder) {
    this.name = name;
    this.description = description;
    this.founder = founder;
  }
}
