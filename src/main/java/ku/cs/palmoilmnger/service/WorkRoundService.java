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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkRoundService {

    @Autowired
    WorkRoundRepository workRoundRepository;

    @Autowired
    TransactionRepository transactionRepository;


//    public void insertNew(WorkRound workRound) {
//        workRoundRepository.save(workRound);
//    }

    public List<WorkRound> findByPlantation(Plantation plantation) {
        return workRoundRepository.findByPlantation(plantation);
    }

    public WorkRound findById(String id){
        return workRoundRepository.findById(id).get();
    }

    // Get WorkRoundDTO list to display
    public List<RoundDTO> getRoundDTOListByPlantation(Plantation plantation){
        List<WorkRound> list = this.findByPlantation(plantation);
        List<RoundDTO> roundDTOList = new ArrayList<>();
        for(WorkRound workRound: list){
            RoundDTO roundDTO = this.transformToRoundDTO(workRound.getIdWorkRound());
            roundDTO.setManagerName(workRound.getUser().getName());
            roundDTOList.add(roundDTO);
        }

        return roundDTOList;
    }

    // transform entity to dto
    public RoundDTO transformToRoundDTO(String idWorkRound){
        RoundDTO roundDTO = new RoundDTO();
        String year = idWorkRound.substring(0,4);
        String month = idWorkRound.substring(4, 6);
        String round = idWorkRound.substring(6);
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
        if( isYear && isMonth){
            throw new WorkRoundException("ใส่ปีและเดือนไม่ครบ");
        } else if (isYear) {
            throw new WorkRoundException("ยังไม่ได้เลือกปี");
        } else if (isMonth){
            throw new WorkRoundException("ยังไม่ได้เลือกเดือน");
        }
        int year = Integer.parseInt(roundDTO.getYear());
        int month = Integer.parseInt(roundDTO.getMonth());
        String idRound = String.format("%04d%02d", year, month);

        // Get round By counting workRoundList
//        List<WorkRound> workRoundList = workRoundRepository.findByIdWorkRoundContaining(idRound);
//        int round = workRoundList.size();
//        round += 1;

        // Get round by the last workRound
        int round = 0;
        String lastWorkRound = this.getLastOfWorkRoundByTime(idRound);
        if(lastWorkRound == null){
            round = 1;
        }else{
          String roundText = lastWorkRound.substring(6);
            round = Integer.parseInt(roundText);
            round += 1;
        }

        WorkRound workRound = new WorkRound();
        workRound.setIdWorkRound(idRound + String.format("%02d", round));
        workRound.setUser(user);
        workRound.setPlantation(plantation);

        workRoundRepository.save(workRound);
    }
    // Get Last WorkRound of palm
    public String getLastOfWorkRound(){
        return workRoundRepository.maxValue();
    }

    // Get Last WorkRound by year and month of palm
    public String getLastOfWorkRoundByTime(String time){
        return workRoundRepository.maxValueByTime(time);
    }

    public void deleteRound(String idRound) throws WorkRoundException {
        WorkRound workRound = findById(idRound);
        List<Transaction> transactionList = transactionRepository.findByWorkRound(workRound);
        if(transactionList.isEmpty()){
            workRoundRepository.deleteById(idRound);
        }else{
            throw new WorkRoundException("ต้องลบรายการของรอบนี้ทั้งหมดนี้");
        }

    }
}
