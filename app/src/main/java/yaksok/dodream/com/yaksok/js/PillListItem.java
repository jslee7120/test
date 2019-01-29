package yaksok.dodream.com.yaksok.js;

public class PillListItem {
    private int medicineNo;
    private String name;
    private String regiDate;
    private int pill_img,pill_gotoright;

    public int getPill_img() {
        return pill_img;
    }

    public void setPill_img(int pill_img) {
        this.pill_img = pill_img;
    }

    public int getPill_gotoright() {
        return pill_gotoright;
    }

    public void setPill_gotoright(int pill_gotoright) {
        this.pill_gotoright = pill_gotoright;
    }

    public int getMedicineNo() {
        return medicineNo;
    }

    public void setMedicineNo(int medicineNo) {
        this.medicineNo = medicineNo;
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
}
