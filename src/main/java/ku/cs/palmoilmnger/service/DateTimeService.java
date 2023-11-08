package ku.cs.palmoilmnger.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateTimeService {

    public List<Integer> getYearListByRange(int range) {
        List<Integer> yearList = new ArrayList<>();
        LocalDate localDateTime = LocalDate.now();
        int year = localDateTime.getYear();
        for (int i = 0; i < range; i++) {
            yearList.add(year);
            year -= 1;
        }

        return yearList;
    }

    public Integer getYear() {
        return LocalDate.now().getYear();
    }

    public Integer getMonth() {
        return LocalDate.now().getMonthValue();
    }

    public String getMonthTextThai(String nthMonth) {
        int numMonth = Integer.parseInt(nthMonth);

        return switch (numMonth) {
            case 1 -> "มกราคม";
            case 2 -> "กุมภาพันธ์";
            case 3 -> "มีนาคม";
            case 4 -> "เมษายน";
            case 5 -> "พฤษภาคม";
            case 6 -> "มิถุนายน";
            case 7 -> "กรกฎาคม";
            case 8 -> "สิงหาคม";
            case 9 -> "กันยายน";
            case 10 -> "ตุลาคม";
            case 11 -> "พฤศจิกายน";
            case 12 -> "ธันวาคม";
            default -> "";
        };
    }

    public int getMonthTextToNumber(String monthStr) {

        return switch (monthStr) {
            case "มกราคม" -> 1;
            case "กุมภาพันธ์" -> 2;
            case "มีนาคม" -> 3;
            case "เมษายน" -> 4;
            case "พฤษภาคม" -> 5;
            case "มิถุนายน" -> 6;
            case "กรกฎาคม" -> 7;
            case "สิงหาคม" -> 8;
            case "กันยายน" -> 9;
            case "ตุลาคม" -> 10;
            case "พฤศจิกายน" -> 11;
            case "ธันวาคม" -> 12;
            default -> 1;
        };
    }

    public List<String> getAllMonthByNow() {
        List<String> strings = new ArrayList<>();

        for (int i = 1; i <= getMonth(); i++) {
            strings.add(getMonthTextThai(String.valueOf(i)));
        }

        return strings;
    }

    public List<String> getAllMonth() {
        List<String> strings = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            strings.add(getMonthTextThai(String.valueOf(i)));
        }

        return strings;
    }
}
