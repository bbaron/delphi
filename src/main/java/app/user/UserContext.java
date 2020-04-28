package app.user;

import java.util.Optional;

public interface UserContext {
  Optional<DelphiUser> getCurrentUser();

  void setCurrentUser(DelphiUser user);
}
