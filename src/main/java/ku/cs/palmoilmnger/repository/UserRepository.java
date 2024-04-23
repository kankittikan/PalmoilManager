package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByName(String name);

    List<User> findByRole(String role);
}
