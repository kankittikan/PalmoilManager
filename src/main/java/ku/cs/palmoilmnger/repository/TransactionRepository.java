package ku.cs.palmoilmnger.repository;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByWorkRound(WorkRound workRound);
    List<Transaction> findByWorkRoundAndDescription(WorkRound workRound, Description description);

    @Query(value = "SELECT MAX(idTransaction) FROM Transaction WHERE idTransaction LIKE :idRound%")
    public String maxValueByTime(@Param("idRound") String idRound);


    @Query("SELECT t FROM Transaction t JOIN t.description d JOIN d.workType w JOIN t.workRound wr WHERE w.name = :name AND wr.idWorkRound = :idRound")
    public List<Transaction> findByWorkTypeAndIdWorkRound(@Param("name") String name, @Param("idRound") String idRound);

    public Transaction findByIdTransaction(String idTransaction);

    public List<Transaction> findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkType(String roundId, int workTypeId);
}
