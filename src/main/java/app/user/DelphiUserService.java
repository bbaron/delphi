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

  public Optional<UserRoles> loadUserAndRoles(String email) {
    return delphiUserRepository.findByEmail(email)
        .map(UserRoles::of);
  }

}
