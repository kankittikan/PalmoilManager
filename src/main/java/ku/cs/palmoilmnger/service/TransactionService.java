package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.exception.TransactionException;
import ku.cs.palmoilmnger.model.TransactionDTO;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public void createTransaction(TransactionDTO transactionDTO, Description description, WorkRound workRound) throws TransactionException {
        if(transactionDTO.getTransactionType().isEmpty() && transactionDTO.getNumberOfTransaction().isEmpty()){
            throw new TransactionException("ยังไม่ได้เลือกประเภทงานและจำนวน");
        } else if (transactionDTO.getTransactionType().isEmpty()) {
            throw new TransactionException("ยังไม่ได้เลือกประเภท");
        } else if (transactionDTO.getNumberOfTransaction().isEmpty()){
            throw new TransactionException("ยังไม่ได้ใส่จำนวน");
        }

        try{
            double number = Double.parseDouble(transactionDTO.getNumberOfTransaction());
            if(number < 0){
                throw new TransactionException("จำนวนต้องมากกว่า 0");
            }

            int count = 0;
            String lastTransaction = transactionRepository.maxValueByTime(workRound.getIdWorkRound());
            if(lastTransaction == null){
                count = 1;
            }else{
                String id = lastTransaction.substring(9);
                count = Integer.parseInt(id);
                count += 1;
            }

            Transaction transaction = new Transaction();
            transaction.setIdTransaction(workRound.getIdWorkRound()+String.format("%03d", count));
            transaction.setValue(Double.parseDouble(transactionDTO.getNumberOfTransaction()));
            transaction.setDescription(description);
            transaction.setDateAdded(LocalDateTime.now());
            // input image
            transaction.setImageLink("default");
            transaction.setWorkRound(workRound);

            transactionRepository.save(transaction);

        }catch (NumberFormatException e){
            throw new TransactionException("ใส่จำนวนเป็นตัวเลขเท่านั้น");
        }

    }

    public List<Transaction> getTransactionByWorkRound(WorkRound workRound){
        return transactionRepository.findByWorkRound(workRound);
    }

    public List<Transaction> getTransactionsByWorkType(WorkType workType, WorkRound worRound){
        return transactionRepository.findByWorkTypeAndIdWorkRound(workType.getName(), worRound.getIdWorkRound());
    }

    public Transaction getTransactionById(String idTransaction){
        return transactionRepository.findByIdTransaction(idTransaction);
    }

    public void deleteTransaction(Transaction transaction){
        transactionRepository.delete(transaction);
    }

    public List<Transaction> getTransactionsByWorkType(WorkType workType, WorkRound worRound, String fieldName, boolean isDesc){
        if(isDesc){
            return transactionRepository.findByWorkTypeAndIdWorkRound(workType.getName(), worRound.getIdWorkRound(), Sort.by(fieldName).descending());
        }
        return transactionRepository.findByWorkTypeAndIdWorkRound(workType.getName(), worRound.getIdWorkRound(), Sort.by(fieldName));
    }
}
