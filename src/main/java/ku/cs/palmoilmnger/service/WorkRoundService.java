package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.Transaction;
import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.exception.WorkRoundException;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.repository.TransactionRepository;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkRoundService {

    @Autowired
    WorkRoundRepository workRoundRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DateTimeService dateTimeService;


//    public void insertNew(WorkRound workRound) {
//        workRoundRepository.save(workRound);
//    }

    public List<WorkRound> findByPlantation(Plantation plantation) {
        return workRoundRepository.findByPlantation(plantation);
    }

    public List<WorkRound> findBySortByPlantation(Plantation plantation) {
        return workRoundRepository.findByPlantation(plantation, Sort.by("idWorkRound").descending());
    }

    public WorkRound findById(String id) {
        return workRoundRepository.findById(id).get();
    }

    // Get WorkRoundDTO list to display
    public List<RoundDTO> getRoundDTOListByPlantation(Plantation plantation) {
        List<WorkRound> list = this.findByPlantation(plantation);
        List<RoundDTO> roundDTOList = new ArrayList<>();
        for (WorkRound workRound : list) {
            RoundDTO roundDTO = this.transformToRoundDTO(workRound.getIdWorkRound());
            roundDTO.setManagerName(workRound.getUser().getName());
            roundDTOList.add(roundDTO);
        }

        return roundDTOList;
    }

    public List<RoundDTO> getRoundDTOListBySortByPlantation(Plantation plantation) {
        List<WorkRound> list = this.findBySortByPlantation(plantation);
        List<RoundDTO> roundDTOList = new ArrayList<>();
        for (WorkRound workRound : list) {
            RoundDTO roundDTO = this.transformToRoundDTO(workRound.getIdWorkRound());
            roundDTO.setManagerName(workRound.getUser().getName());
            roundDTOList.add(roundDTO);
        }
        return roundDTOList;
    }

    // transform entity to dto
    public RoundDTO transformToRoundDTO(String idWorkRound) {
        RoundDTO roundDTO = new RoundDTO();
        String year = idWorkRound.substring(3, 7);
        String month = idWorkRound.substring(7, 9);
        String round = idWorkRound.substring(9);
        roundDTO.setIdWorkRound(idWorkRound);
        roundDTO.setYear(year);
        roundDTO.setMonth(month);
        roundDTO.setRound(round);
        return roundDTO;
    }

    public void createWorkRoundRepeat(RoundDTO roundDTO, Plantation plantation, User user) throws WorkRoundException {
        // create string id
        boolean isYear = roundDTO.getYear().equals("0");
        boolean isMonth = roundDTO.getMonth().equals("0");
        if (isYear && isMonth) {
            throw new WorkRoundException("ใส่ปีและเดือนไม่ครบ");
        } else if (isYear) {
            throw new WorkRoundException("ยังไม่ได้เลือกปี");
        } else if (isMonth) {
            throw new WorkRoundException("ยังไม่ได้เลือกเดือน");
        }
        int year = Integer.parseInt(roundDTO.getYear());
        int month = dateTimeService.getMonthTextToNumber(roundDTO.getMonth());
        String idRound = String.format("%03d%04d%02d", plantation.getIdPlantation(), year, month);

        // Get round By counting workRoundList
//        List<WorkRound> workRoundList = workRoundRepository.findByIdWorkRoundContaining(idRound);
//        int round = workRoundList.size();
//        round += 1;

        // Get round by the last workRound
        int round = 0;
        List<WorkRound> workRounds = workRoundRepository.findByPlantation_IdPlantationAndIdWorkRoundContains(plantation.getIdPlantation(), idRound);
        if (workRounds.isEmpty()) {
            round = 1;
        } else {
            String roundText = workRounds.get(workRounds.size() - 1).getIdWorkRound().substring(9);
            round = Integer.parseInt(roundText);
            round += 1;
        }

        WorkRound workRound = new WorkRound();
        workRound.setIdWorkRound(idRound + String.format("%02d", round));
        workRound.setUser(user);
        workRound.setPlantation(plantation);
        workRoundRepository.save(workRound);
    }

    public void deleteRound(String idRound) throws WorkRoundException {
        WorkRound workRound = findById(idRound);
        List<Transaction> transactionList = transactionRepository.findByWorkRound(workRound);
        if (transactionList.isEmpty()) {
            workRoundRepository.deleteById(idRound);
        } else {
            throw new WorkRoundException("ต้องลบรายการของรอบนี้ทั้งหมดนี้");
        }

    }

//    public WorkRound getWorkRoundByTransaction(Transaction transaction){
//        return workRoundRepository.findByTransaction(transaction);
//    }
}
