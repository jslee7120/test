package yaksok.dodream.com.yaksok.vo;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import yaksok.dodream.com.yaksok.vo.message.MessageBodyVO;

public class ShopVO {
    @SerializedName("status") String status;
    @SerializedName("result") List<Result> result;



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

    public ShopVO(){}
    //private int messageNo;
    //    private int givingUserMyMedicineNo;
    //    private String givingUser;//사용자
    //    private String receivingUser;//상대방
    //    private String content;
    //    private String regiDate;
    public class Result{


        @SerializedName("imageUri")@Expose private String imageUri;
        @SerializedName("productName")@Expose private String productName;
        @SerializedName("productCategory")@Expose private String productCategoryr;
        @SerializedName("productEffectCategory")@Expose private String productEffectCategory;

        public String getImageUri() {
            return imageUri;
        }

        public void setImageUri(String imageUri) {
            this.imageUri = imageUri;
        }

        public String getProductCategoryr() {
            return productCategoryr;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductCategoryry() {
            return productCategoryr;
        }

        public void setProductCategoryr(String productCategoryr) {
            this.productCategoryr = productCategoryr;
        }

        public String getProductEffectCategory() {
            return productEffectCategory;
        }

        public void setProductEffectCategory(String productEffectCategory) {
            this.productEffectCategory = productEffectCategory;
        }
    }
}
