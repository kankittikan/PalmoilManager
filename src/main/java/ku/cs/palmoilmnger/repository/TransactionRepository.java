package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.nio.file.Watchable;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByWorkRound(WorkRound workRound);
    List<Transaction> findByWorkRoundAndDescription(WorkRound workRound, Description description);

    @Query(value = "SELECT MAX(idTransaction) FROM Transaction WHERE idTransaction LIKE :idRound%")
    public String maxValueByTime(@Param("idRound") String idRound);
}
