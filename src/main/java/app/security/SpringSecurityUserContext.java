package app.security;

import app.user.DelphiUser;
import app.user.UserContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityUserContext implements UserContext {
  @Override
  public Optional<DelphiUser> getCurrentUser() {
    return Securities.authentication()
        .map(auth -> (DelphiUser) auth.getPrincipal());
  }

  @Override
  public void setCurrentUser(DelphiUser user) {
    var auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    Securities.setAuthentication(auth);
  }
}
