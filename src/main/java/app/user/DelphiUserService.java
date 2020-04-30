package app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DelphiUserService {
  private final DelphiUserRepository delphiUserRepository;

  public Optional<DelphiUser> loadUserAndRoles(String email) {
    return delphiUserRepository.findByEmail(email);
  }

}
