package app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static java.util.Optional.*;

public final class Securities {
  private Securities() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static SecurityContext securityContext() {
    return SecurityContextHolder.getContext();
  }

  public static Optional<Authentication> authentication() {
    return ofNullable(securityContext().getAuthentication());
  }

  public static void setAuthentication(Authentication auth) {
    securityContext().setAuthentication(auth);
  }
}
