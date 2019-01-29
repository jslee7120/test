package yaksok.dodream.com.yaksok.vo;

import com.google.gson.annotations.SerializedName;

public class FamilyBodyVO {

    @SerializedName("status") String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
