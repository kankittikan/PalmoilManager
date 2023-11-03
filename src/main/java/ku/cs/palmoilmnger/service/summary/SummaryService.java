package ku.cs.palmoilmnger.service.summary;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.model.TransactionSummaryDTO;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import ku.cs.palmoilmnger.repository.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryService {
    @Autowired
    private TransactionRepository transactionRepository;

    private void addValue(List<TransactionSummaryDTO> transactionSummaryDTOS, Transaction transaction) {
        boolean found = false;
        for (TransactionSummaryDTO t : transactionSummaryDTOS) {
            if (t.getDescription().equals(transaction.getDescription().getName())) {
                t.setValue(String.valueOf(Double.parseDouble(t.getValue()) + transaction.getValue()));
                found = true;
            }
        }
        if (!found) {
            TransactionSummaryDTO transactionSummaryDTO = new TransactionSummaryDTO();
            transactionSummaryDTO.setDescription(transaction.getDescription().getName());
            transactionSummaryDTO.setValue(String.valueOf(transaction.getValue()));
            transactionSummaryDTO.setDescriptionType(transaction.getDescription().getDescriptionType().getName());
            transactionSummaryDTOS.add(transactionSummaryDTO);
        }
    }

    public void sumUpQuarter(int year, int quarter, Plantation plantation) throws IOException {
        List<TransactionSummaryDTO> palmTransaction = new ArrayList<>();
        List<TransactionSummaryDTO> fertilizeTransaction = new ArrayList<>();
        List<TransactionSummaryDTO> trimTransaction = new ArrayList<>();
        int start = 0;
        String roundId;

        if (quarter == 1) start = 1;
        if (quarter == 2) start = 4;
        if (quarter == 3) start = 7;
        if (quarter == 4) start = 10;

        for (int i = 0; i < 3; i++) {
            roundId = year + String.valueOf(start++);
            List<Transaction> transactionList = transactionRepository.findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkType(roundId, 1);
            for (Transaction t : transactionList) {
                addValue(palmTransaction, t);
            }
        }

        PDFExporter pdfExporter = new QuarterPDFExporter(plantation, palmTransaction, fertilizeTransaction, trimTransaction, quarter);
        pdfExporter.export();
    }
}
