package app.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
public class DelphiUser implements UserDetails {
  @Id
  @GeneratedValue
  private Long id;
  @Email
  @NotNull
  @Column(unique = true)
  private String email;

  private String password;

  private Boolean enabled;

  private boolean admin = false;

  private static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
  private static final Collection<GrantedAuthority> USER = List.of(ROLE_USER);
  private static final Collection<GrantedAuthority> ADMIN = List.of(ROLE_USER, new SimpleGrantedAuthority("ROLE_ADMIN"));

  public DelphiUser(String email, String password) {
    this.email = email;
    this.password = password;
    this.enabled = true;
  }

  public static DelphiUser administrator(String email, String password) {
    var u = new DelphiUser(email, password);
    u.setAdmin(true);
    return u;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return admin ? ADMIN : USER;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
