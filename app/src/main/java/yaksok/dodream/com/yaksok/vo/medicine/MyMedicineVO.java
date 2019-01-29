package yaksok.dodream.com.yaksok.vo.medicine;

public class MyMedicineVO {

    private String myMedicineNo;
    private String userId;
    private String name;
    private String regiDate;

    private int dosagi;

    public String getMyMedicineNo() {
        return myMedicineNo;
    }

    public void setMyMedicineNo(String myMedicineNo) {
        this.myMedicineNo = myMedicineNo;
    }

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

    public String getRegiDate() {
        return regiDate;
    }

    public void setRegiDate(String regiDate) {
        this.regiDate = regiDate;
    }

    public int getDosagi() {
        return dosagi;
    }

    public void setDosagi(int dosagi) {
        this.dosagi = dosagi;
    }
}
