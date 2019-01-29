package yaksok.dodream.com.yaksok.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyMedicineResponseTypeVO {


    @SerializedName("status") String status;
    @SerializedName("result") List<Result> result;
    MyMedicineResponseTypeVO(){}

    public class Result {
        @SerializedName("medicinNo")@Expose private int medicineNo;
        @SerializedName("name") @Expose private String name;
        @SerializedName("regiDate") @Expose private String regiDate;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
