package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Watchable;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByWorkRoundAndWorkType(WorkRound workRound, WorkType workType);
    List<Transaction> findByWorkRound(WorkRound workRound);

    List<Transaction> findByWorkRoundAndDescription(WorkRound workRound, Description description);
}
