package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public void createTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionByWorkRound(WorkRound workRound){
        return transactionRepository.findByWorkRound(workRound);
    }
}
