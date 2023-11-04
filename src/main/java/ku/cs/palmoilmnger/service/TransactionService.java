package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.exception.StorageException;
import ku.cs.palmoilmnger.exception.TransactionException;
import ku.cs.palmoilmnger.model.FertilizeFormulaDTO;
import ku.cs.palmoilmnger.model.TransactionDTO;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    DescriptionService descriptionService;

    public void createTransaction(TransactionDTO transactionDTO, Description description, WorkRound workRound, MultipartFile multipartFile) throws TransactionException, IOException, StorageException {
        if (transactionDTO.getTransactionType().isEmpty() && transactionDTO.getNumberOfTransaction().isEmpty()) {
            throw new TransactionException("ยังไม่ได้เลือกประเภทงานและจำนวน");
        } else if (transactionDTO.getTransactionType().isEmpty()) {
            throw new TransactionException("ยังไม่ได้เลือกประเภท");
        } else if (transactionDTO.getNumberOfTransaction().isEmpty()) {
            throw new TransactionException("ยังไม่ได้ใส่จำนวน");
        }

        try {
            double number = Double.parseDouble(transactionDTO.getNumberOfTransaction());
            if (number <= 0) throw new TransactionException("ตัวเลขห้ามติดลบ");
            int count = 0;
            String lastTransaction = transactionRepository.maxValueByTime(workRound.getIdWorkRound());
            if (lastTransaction == null) {
                count = 1;
            } else {
                String id = lastTransaction.substring(11);
                count = Integer.parseInt(id);
                count += 1;
            }

            if (!description.isRepeatable()) {
                if (!transactionRepository.findByWorkRoundAndDescription(workRound, description).isEmpty())
                    throw new TransactionException("รายการนี้ซ้ำไม่ได้");
            }

            Transaction transaction = new Transaction();
            transaction.setIdTransaction(workRound.getIdWorkRound() + String.format("%03d", count));
            transaction.setValue(number);
            transaction.setDescription(description);
            transaction.setDateAdded(LocalDateTime.now());

            // input image
            if (multipartFile.getSize() != 0) {
                String imgName = storageService.addImg(multipartFile);
                transaction.setImageLink(imgName);
            }

            transaction.setWorkRound(workRound);
            transactionRepository.save(transaction);

        } catch (NumberFormatException e) {
            throw new TransactionException("ใส่ตัวเลขเท่านั้น");
        }

    }

    public void createFertilizeFormula(FertilizeFormulaDTO fertilizeFormulaDTO, WorkRound workRound) throws TransactionException {
        if (fertilizeFormulaDTO.getK().isEmpty() || fertilizeFormulaDTO.getP().isEmpty() || fertilizeFormulaDTO.getN().isEmpty())
            throw new TransactionException("ใส่ข้อมูลไม่ครบ");
        int count = 0;
        String lastTransaction = transactionRepository.maxValueByTime(workRound.getIdWorkRound());
        if (lastTransaction == null) {
            count = 1;
        } else {
            String id = lastTransaction.substring(11);
            count = Integer.parseInt(id);
            count += 1;
        }

        int n, p, k;
        try {
            n = Integer.parseInt(fertilizeFormulaDTO.getN());
            p = Integer.parseInt(fertilizeFormulaDTO.getP());
            k = Integer.parseInt(fertilizeFormulaDTO.getK());
        } catch (NumberFormatException e) {
            throw new TransactionException("ใส่ข้อมูลตัวเลขเท่านั้น");
        }

        if (n < 0 || p < 0 || k < 0) throw new TransactionException("ตัวเลขห้ามติดลบ");
        if (n > 99 || p > 99 || k > 99) throw new TransactionException("ตัวเลขห้ามเกิน 100");

        Description description = descriptionService.getDescriptionByName("สูตรปุ๋ย");

        if (!description.isRepeatable()) {
            if (!transactionRepository.findByWorkRoundAndDescription(workRound, description).isEmpty())
                throw new TransactionException("รายการนี้ซ้ำไม่ได้");
        }

        Transaction transaction = new Transaction();
        transaction.setIdTransaction(workRound.getIdWorkRound() + String.format("%03d", count));
        transaction.setValue(Double.parseDouble(1 + String.format("%02d", n) + String.format("%02d", p) + String.format("%02d", k)));
        transaction.setDescription(description);
        transaction.setDateAdded(LocalDateTime.now());

        transaction.setWorkRound(workRound);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionByWorkRound(WorkRound workRound) {
        return transactionRepository.findByWorkRound(workRound);
    }

    public List<Transaction> getTransactionsByWorkType(WorkType workType, WorkRound worRound) {
        return transactionRepository.findByWorkTypeAndIdWorkRound(workType.getName(), worRound.getIdWorkRound());
    }

    public Transaction getTransactionById(String idTransaction) {
        return transactionRepository.findByIdTransaction(idTransaction);
    }

    public void deleteTransaction(Transaction transaction) {
        storageService.deleteImg(transaction.getImageLink());
        transactionRepository.delete(transaction);
    }
}
