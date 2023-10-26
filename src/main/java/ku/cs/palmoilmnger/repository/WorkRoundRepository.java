package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.WorkRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRoundRepository extends JpaRepository<WorkRound, String> {
    public List<WorkRound> findByPlantation(Plantation plantation);
}
