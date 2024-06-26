package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Description;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.entity.WorkType;
import ku.cs.palmoilmnger.exception.StorageException;
import ku.cs.palmoilmnger.exception.TransactionException;
import ku.cs.palmoilmnger.model.CalculateRateDTO;
import ku.cs.palmoilmnger.model.FertilizeFormulaDTO;
import ku.cs.palmoilmnger.model.TransactionDTO;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

            if(description.getName().contains("บาท") && number >= 1000000) throw new TransactionException("จำนวนเงินห้ามเกิน 1 ล้านบาท");
            if(description.getName().contains("กก") && number > 30000) throw new TransactionException("น้ำหนักห้ามเกิน 30 ตัน");
            if(description.getName().contains("บาท/กก") && number > 20) throw new TransactionException("ราคาปาล์มห้ามเกิน 20 บาท/กก");
            if(description.getName().contains("ปริมาณปุ๋ย")) {
                int maxWeight = (workRound.getPlantation().getNumOfRaiUnit() * 21) * 4;
                if(number > maxWeight) throw new TransactionException("ปริมาณปุ๋ยห้ามเกิน " + maxWeight + " กก ในแปลงนี้");
            }

            Transaction transaction = new Transaction();
            transaction.setIdTransaction(workRound.getIdWorkRound() + String.format("%03d", count));
            transaction.setValue(number);
            transaction.setDescription(description);
            transaction.setDateAdded(LocalDateTime.now(ZoneId.of("Asia/Bangkok")));

                // input image
            if (multipartFile != null && multipartFile.getSize() != 0) {
                String imgName = storageService.addImg(multipartFile);
                transaction.setImageLink(imgName);
            }

            transaction.setWorkRound(workRound);

//            List<Transaction> transactionList = transactionRepository.findByWorkRoundAndDescription(workRound, description);
//            transactionList = transactionList.stream().filter(transaction1 -> transaction1.getDateAdded().toLocalDate().equals(transaction.getDateAdded().toLocalDate()) && transaction1.getDescription() == description).toList();
//            if(!transactionList.isEmpty()) throw new TransactionException("วันนี้รายการนี้ถูกเพิ่มไปแล้ว");

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
        if (n >= 50 || p >= 50 || k >= 50) throw new TransactionException("ตัวเลขห้ามเกิน 50");

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

    public void calRate(CalculateRateDTO calculateRateDTO, WorkRound workRound) throws TransactionException {
        if(calculateRateDTO.getRateTransfer().isEmpty() || calculateRateDTO.getRateCollect().isEmpty()) throw new TransactionException("ข้อมูลว่าง");
        double rateTransfer, rateCollect;

        try {
            rateTransfer = Double.parseDouble(calculateRateDTO.getRateTransfer());
            rateCollect = Double.parseDouble(calculateRateDTO.getRateCollect());
        }
        catch (NumberFormatException e) {
            throw new TransactionException("ต้องเป็นตัวเลขเท่านั้น");
        }

        if(rateTransfer <= 0 || rateCollect <= 0) throw new TransactionException("ตัวเลขห้ามต่ำกว่า 0");

        double sumWeight = 0;
        List<Transaction> transactionList = transactionRepository.findByWorkTypeAndIdWorkRound("ตัดปาล์ม", workRound.getIdWorkRound());
        for(Transaction t : transactionList) sumWeight += t.getValue();

        System.out.println(sumWeight);

        double transferPrice = sumWeight * rateTransfer;
        double collectPrice = sumWeight * rateCollect;

        Description descriptionTransfer = descriptionService.getDescriptionByName("ค่าบรรทุก (บาท)");
        Description descriptionCollect = descriptionService.getDescriptionByName("ค่าเก็บเกี่ยว (บาท)");

        List<Transaction> transactionListCheck = transactionRepository.findByWorkRoundAndDescription(workRound, descriptionCollect);
        transactionListCheck = transactionListCheck.stream().filter(transaction1 -> transaction1.getDescription() == descriptionCollect).toList();
        if(!transactionListCheck.isEmpty()) throw new TransactionException("รายการนี้ถูกคำนวณแล้ว");
        transactionListCheck = transactionRepository.findByWorkRoundAndDescription(workRound, descriptionTransfer);
        transactionListCheck = transactionListCheck.stream().filter(transaction1 -> transaction1.getDescription() == descriptionTransfer).toList();
        if(!transactionListCheck.isEmpty()) throw new TransactionException("รายการนี้ถูกคำนวณแล้ว");

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setNumberOfTransaction(String.valueOf(transferPrice));
        transactionDTO.setTransactionType("ค่าบรรทุก (บาท)");
        try {
            createTransaction(transactionDTO, descriptionTransfer, workRound, null);
        } catch (IOException | StorageException | TransactionException e) {
            throw new TransactionException("เพิ่มรายการไม่สำเร็จ");
        }

        transactionDTO = new TransactionDTO();
        transactionDTO.setNumberOfTransaction(String.valueOf(collectPrice));
        transactionDTO.setTransactionType("ค่าเก็บเกี่ยว (บาท)");
        try {
            createTransaction(transactionDTO, descriptionCollect, workRound, null);
        } catch (IOException | StorageException | TransactionException e) {
            throw new TransactionException("เพิ่มรายการไม่สำเร็จ");
        }
    }
}
