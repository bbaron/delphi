package app.security;

import app.user.DelphiUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsManager implements UserDetailsService {
  private final DelphiUserService delphiUserService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return delphiUserService.loadUserAndRoles(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
