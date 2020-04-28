package app.user;

import app.user.DelphiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DelphiUserRepository extends JpaRepository<DelphiUser, Long> {
  Optional<DelphiUser> findByEmail(String email);
}
