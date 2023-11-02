package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkRoundRepository extends JpaRepository<WorkRound, String> {
    public List<WorkRound> findByPlantation(Plantation plantation);
    public List<WorkRound> findByPlantation(Plantation plantation, Sort sort);

    //public List<WorkRound> findByIdWorkRoundOrderByPlantationAsc(Plantation plantation);

    List<WorkRound> findByIdWorkRoundContaining(String id);

    @Query(value = "SELECT MAX(idWorkRound) FROM WorkRound")
    public String maxValue();

    @Query(value = "SELECT MAX(idWorkRound) FROM WorkRound WHERE idWorkRound LIKE :time%")
    public String maxValueByTime(@Param("time") String time);

}
