package yaksok.dodream.com.yaksok.js;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicineVOList {
    @SerializedName("status") String status;
    @SerializedName("result") List<Result> result;
    MedicineVOList(){}

    public class Result {
        @SerializedName("name") @Expose private String name;
        @SerializedName("element") @Expose private String element;
        @SerializedName("company") @Expose private String company;
        @SerializedName("medicineNo") @Expose private int medicineNo;

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String getElement() { return element; }

        public void setElement(String element) { this.element = element; }

        public String getCompany() { return company; }

        public void setCompany(String company) { this.company = company; }

        public int getMedicineNo() { return medicineNo; }

        public void setMedicineNo(int medicineNo) { this.medicineNo = medicineNo; }
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public List<Result> getResult() { return result; }

    public void setResult(List<Result> result) { this.result = result; }
}
