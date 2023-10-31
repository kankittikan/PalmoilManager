package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.entity.Plantation;
import ku.cs.palmoilmnger.entity.User;
import ku.cs.palmoilmnger.entity.WorkRound;
import ku.cs.palmoilmnger.exception.WorkRoundException;
import ku.cs.palmoilmnger.model.RoundDTO;
import ku.cs.palmoilmnger.repository.WorkRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkRoundService {

    @Autowired
    WorkRoundRepository workRoundRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlantationService plantationService;

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
        List<WorkRound> workRoundList = workRoundRepository.findByIdWorkRoundContaining(idRound);
        int round = workRoundList.size();
        round += 1;
        WorkRound workRound = new WorkRound();
        workRound.setIdWorkRound(idRound + String.format("%02d", round));
        workRound.setUser(user);
        workRound.setPlantation(plantation);

        workRoundRepository.save(workRound);
    }

//    private String workRoundId(int year, int month){
//        int count = 0;
//        int i=1;
//        String idRound = String.format("%04d%02d", year, month);
//        while(true){
//            String check = idRound + String.format("%02d", i);
//            Optional<WorkRound> workRoundOptional = workRoundRepository.findById(check);
//            if(workRoundOptional.isEmpty()){
//                idRound = check;
//                break;
//            }
//            count += 1;
//            i += 1;
//        }
//        return idRound;
//   }
}
