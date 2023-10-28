package ku.cs.palmoilmnger.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateTimeService {

    public List<Integer> getYearListByRange(int range){
        List<Integer> yearList = new ArrayList<>();
        LocalDate localDateTime = LocalDate.now();
        int year = localDateTime.getYear();
        for(int i=0; i<range; i++){
            yearList.add(year);
            year -= 1;
        }

        return yearList;
    }

    public Integer getYear(){
        return LocalDate.now().getYear();
    }

    public Integer getMonth(){
        return LocalDate.now().getMonthValue();
    }
}
