package yaksok.dodream.com.yaksok.js;

import java.util.List;

public class InsertPillList {
    private String userId,name,dosagi;
    private List<String> timeList;
    private List<Integer> elementList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosagi() {
        return dosagi;
    }

    public void setDosagi(String dosagi) {
        this.dosagi = dosagi;
    }

    public List<String> getTimeList() { return timeList; }

    public void setTimeList(List<String> timeList) { this.timeList = timeList; }

    public List<Integer> getElementList() { return elementList; }

    public void setElementList(List<Integer> elementList) { this.elementList = elementList; }
}
