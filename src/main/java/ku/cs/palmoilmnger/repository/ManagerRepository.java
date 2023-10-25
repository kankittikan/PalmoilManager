package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
