package yaksok.dodream.com.yaksok.vo;

public class AlramVO {

    private String givingUser;
    private String receivingUser;
    private String regiDate;
    private int alramNo;
    private int myMedicineNo;

    public String getGivingUser() {
        return givingUser;
    }

    public void setGivingUser(String givingUser) {
        this.givingUser = givingUser;
    }

    public String getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }

    public String getRegiDate() {
        return regiDate;
    }

    public void setRegiDate(String regiDate) {
        this.regiDate = regiDate;
    }

    public int getAlramNo() {
        return alramNo;
    }

    public void setAlramNo(int alramNo) {
        this.alramNo = alramNo;
    }

    public int getMyMedicineNo() {
        return myMedicineNo;
    }

    public void setMyMedicineNo(int myMedicineNo) {
        this.myMedicineNo = myMedicineNo;
    }
}
