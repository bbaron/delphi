package app.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public record UserRoles(DelphiUser user, Set<GrantedAuthority>roles) implements UserDetails {

  public UserRoles addRole(GrantedAuthority grantedAuthority) {
    roles.add(grantedAuthority);
    return this;
  }

  public static UserRoles of(DelphiUser user) {
    return new UserRoles(user, new LinkedHashSet<>());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.getEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.getEnabled();
  }

  @Override
  public boolean isEnabled() {
    return user.getEnabled();
  }
}
