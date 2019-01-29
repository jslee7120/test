package yaksok.dodream.com.yaksok.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SnsBodyVO {
    @SerializedName("status") String status;

    public String getStatus() {
        return status;
    }
    public SnsBodyVO(){}


}
