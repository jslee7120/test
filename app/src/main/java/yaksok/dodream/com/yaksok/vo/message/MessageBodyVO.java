package yaksok.dodream.com.yaksok.vo.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import yaksok.dodream.com.yaksok.vo.UserVO;

//BODY{
//	“id” : “…” NOT NULL
//	“phoneNumber” : ”…“ NOT NULL
//	“nickname” : ”…“ NOT NULL
//	“profileImagePath” : “…”
//	“email” : “…”
// 	“birthday” : “…”
//	“ageRange” : “…”
//	“userType”:”…”	  NOT NULL   char형
//}
public class MessageBodyVO {
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

    public MessageBodyVO(){}
    //private int messageNo;
    //    private int givingUserMyMedicineNo;
    //    private String givingUser;//사용자
    //    private String receivingUser;//상대방
    //    private String content;
    //    private String regiDate;
        public class Result{


        @SerializedName("messageNo")@Expose private int messageNo;
        @SerializedName("givingUserMyMedicineNo")@Expose private int givingUserMyMedicineNo;
        @SerializedName("givingUser")@Expose private String givingUser;
        @SerializedName("receivingUser")@Expose private String receivingUser;
        @SerializedName("content") @Expose private String content;
        @SerializedName("regiDate")@Expose private String regiDate;

        public int getMessageNo() {
            return messageNo;
        }

        public void setMessageNo(int messageNo) {
            this.messageNo = messageNo;
        }

        public int getGivingUserMyMedicineNo() {
            return givingUserMyMedicineNo;
        }

        public void setGivingUserMyMedicineNo(int givingUserMyMedicineNo) {
            this.givingUserMyMedicineNo = givingUserMyMedicineNo;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRegiDate() {
            return regiDate;
        }

        public void setRegiDate(String regiDate) {
            this.regiDate = regiDate;
        }
    }
    //HTTP GET   users/{item} ? itemTyp
//ex) /01027250856 ? itemType=phoneNumber
//
//Host:  http://54.180.81.180:8080/users/010?itemType=phoneNumber
//
//request path variable  -  item
//request query paramter - itemType
//
//response
//BODY{
//	“status” : “code”
//	“result” : “UserVO” List
//
//}
//
//code
//200 : OK
//204: 계정 존재하지않음(null반환)
//400: 잘못된 요청(itemType)
//500 : Server Error

}
