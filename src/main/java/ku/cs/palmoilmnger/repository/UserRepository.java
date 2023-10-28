package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
