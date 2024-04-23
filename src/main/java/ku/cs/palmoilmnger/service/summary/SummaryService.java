package ku.cs.palmoilmnger.service.summary;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.exception.PlantationException;
import ku.cs.palmoilmnger.model.SummaryAnnualDTO;
import ku.cs.palmoilmnger.model.SummaryDTO;
import ku.cs.palmoilmnger.model.SummaryQuarterDTO;
import ku.cs.palmoilmnger.model.TransactionSummaryDTO;
import ku.cs.palmoilmnger.repository.PlantationRepository;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import ku.cs.palmoilmnger.repository.WorkTypeRepository;
import ku.cs.palmoilmnger.service.PlantationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SummaryService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PlantationService plantationService;

    private void addValue(List<TransactionSummaryDTO> transactionSummaryDTOS, Transaction transaction) {
        boolean found = false;
        for (TransactionSummaryDTO t : transactionSummaryDTOS) {
            if (t.getDescription().equals(transaction.getDescription().getName())) {
                t.setValue(String.valueOf(Double.parseDouble(t.getValue()) + transaction.getValue()));
                found = true;
            }
        }
        if (!found && !transaction.getDescription().getDescriptionType().getName().equals("อื่น ๆ")) {
            TransactionSummaryDTO transactionSummaryDTO = new TransactionSummaryDTO();
            transactionSummaryDTO.setDescription(transaction.getDescription().getName());
            transactionSummaryDTO.setValue(String.valueOf(transaction.getValue()));
            transactionSummaryDTO.setDescriptionType(transaction.getDescription().getDescriptionType().getName());
            transactionSummaryDTOS.add(transactionSummaryDTO);
        }
    }

    private void addAllType(List<TransactionSummaryDTO> transactionSummaryDTO, SummaryDTO summaryDTO) {
        for (TransactionSummaryDTO t : transactionSummaryDTO) {
            if (t.getDescriptionType().equals("รายรับ")) summaryDTO.addSumOfIncome(Double.parseDouble(t.getValue()));
            if (t.getDescriptionType().equals("รายจ่าย")) summaryDTO.addSumOfOutcome(Double.parseDouble(t.getValue()));
            if (t.getDescription().equals("น้ำหนัก (กก)")) summaryDTO.addSumOfWeight(Double.parseDouble(t.getValue()));
        }
    }

    public void sumUpQuarter(SummaryQuarterDTO summaryQuarterDTO) throws IOException, PlantationException {
        int year = Integer.parseInt(summaryQuarterDTO.getYear());
        int quarter = Integer.parseInt(summaryQuarterDTO.getQuarter());
        Plantation plantation = plantationService.getPlantationByName(summaryQuarterDTO.getPlotName());

        List<TransactionSummaryDTO> palmTransaction = new ArrayList<>();
        List<TransactionSummaryDTO> fertilizeTransaction = new ArrayList<>();
        List<TransactionSummaryDTO> trimTransaction = new ArrayList<>();
        int start = 0;
        String roundId;

        if (quarter == 1) start = 1;
        if (quarter == 2) start = 4;
        if (quarter == 3) start = 7;
        if (quarter == 4) start = 10;


        //get transactions
        for (int i = start; i <= start + 3; i++) {
            roundId = String.format("%03d", plantation.getIdPlantation()) + year + String.format("%02d", i);

            //palm
            List<Transaction> transactionList = transactionRepository.findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkTypeAndDescription_DescriptionType_NameContaining(roundId, 1, "");
            for (Transaction t : transactionList) {
                addValue(palmTransaction, t);
            }
            //fertilize
            List<Transaction> fertilizeList = transactionRepository.findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkTypeAndDescription_DescriptionType_NameContaining(roundId, 2, "ราย");
            for (Transaction t : fertilizeList) {
                addValue(fertilizeTransaction, t);
            }
            //trim
            List<Transaction> trimList = transactionRepository.findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkTypeAndDescription_DescriptionType_NameContaining(roundId, 3, "ราย");
            for (Transaction t : trimList) {
                addValue(trimTransaction, t);
            }
        }

        //sort
        palmTransaction.sort(Comparator.comparing(TransactionSummaryDTO::getDescriptionType));
        fertilizeTransaction.sort(Comparator.comparing(TransactionSummaryDTO::getDescriptionType));
        trimTransaction.sort(Comparator.comparing(TransactionSummaryDTO::getDescriptionType));

        SummaryDTO summaryDTO = new SummaryDTO();

        //add all type value
        addAllType(palmTransaction, summaryDTO);
        addAllType(fertilizeTransaction, summaryDTO);
        addAllType(trimTransaction, summaryDTO);

        //cal npm and productivity
        double np = summaryDTO.getSumOfIncome() - summaryDTO.getSumOfOutcome();
        summaryDTO.setNp(np);
        summaryDTO.setNpm((np / summaryDTO.getSumOfIncome()) * 100);
        summaryDTO.setProductivityRate(summaryDTO.getSumOfWeight() / plantation.getNumOfRaiUnit());

        PDFExporter pdfExporter = new QuarterPDFExporter(plantation, palmTransaction, fertilizeTransaction, trimTransaction, summaryDTO, quarter);
        pdfExporter.export();
    }

    public void sumUpAnnual(SummaryAnnualDTO summaryAnnualDTO) throws IOException, PlantationException {
        int year = Integer.parseInt(summaryAnnualDTO.getYear());
        Plantation plantation = plantationService.getPlantationByName(summaryAnnualDTO.getPlotName());

        List<TransactionSummaryDTO> palmTransaction = new ArrayList<>();
        List<TransactionSummaryDTO> fertilizeTransaction = new ArrayList<>();
        List<TransactionSummaryDTO> trimTransaction = new ArrayList<>();
        String roundId;

        //get transactions
        for (int i = 1; i <= 12; i++) {
            roundId = String.format("%03d", plantation.getIdPlantation()) + year + String.format("%02d", i);

            //palm
            List<Transaction> transactionList = transactionRepository.findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkTypeAndDescription_DescriptionType_NameContaining(roundId, 1, "");
            for (Transaction t : transactionList) {
                addValue(palmTransaction, t);
            }
            //fertilize
            List<Transaction> fertilizeList = transactionRepository.findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkTypeAndDescription_DescriptionType_NameContaining(roundId, 2, "ราย");
            for (Transaction t : fertilizeList) {
                addValue(fertilizeTransaction, t);
            }
            //trim
            List<Transaction> trimList = transactionRepository.findByWorkRound_IdWorkRoundContainingAndDescription_WorkType_IdWorkTypeAndDescription_DescriptionType_NameContaining(roundId, 3, "ราย");
            for (Transaction t : trimList) {
                addValue(trimTransaction, t);
            }
        }

        //sort
        palmTransaction.sort(Comparator.comparing(TransactionSummaryDTO::getDescriptionType));
        fertilizeTransaction.sort(Comparator.comparing(TransactionSummaryDTO::getDescriptionType));
        trimTransaction.sort(Comparator.comparing(TransactionSummaryDTO::getDescriptionType));

        SummaryDTO summaryDTO = new SummaryDTO();

        //add all type value
        addAllType(palmTransaction, summaryDTO);
        addAllType(fertilizeTransaction, summaryDTO);
        addAllType(trimTransaction, summaryDTO);

        //cal npm and productivity
        double np = summaryDTO.getSumOfIncome() - summaryDTO.getSumOfOutcome();
        summaryDTO.setNp(np);
        summaryDTO.setNpm((np / summaryDTO.getSumOfIncome()) * 100);
        summaryDTO.setProductivityRate((summaryDTO.getSumOfWeight() / 1000) / plantation.getNumOfRaiUnit());

        PDFExporter pdfExporter = new AnnualPDFExporter(plantation, palmTransaction, fertilizeTransaction, trimTransaction, summaryDTO, String.valueOf(year));
        pdfExporter.export();
    }
}
