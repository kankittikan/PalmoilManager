package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Plantation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantationRepository extends JpaRepository<Plantation, Integer> {
    Plantation findByName(String name);
}
