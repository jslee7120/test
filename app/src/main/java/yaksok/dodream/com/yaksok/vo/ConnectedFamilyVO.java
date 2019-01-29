package yaksok.dodream.com.yaksok.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
public class ConnectedFamilyVO {
    @SerializedName("status") String status;
    @SerializedName("result") List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }



    public ConnectedFamilyVO(){}
        //“id” : “…” NOT NULL
        //	“phoneNumber” : ”…“ NOT NULL
        //	“pw” : ”…“ NOT NULL
        //	“nickname” : ”…“ NOT NULL
        //	“profileImagePath” : “…”
        //	“email” : “…”
        // 	“birthday” : “…”
        //	“ageRange” : “…”
        //	“userType”:”…”	  NOT NULL   char형
        public class Result{


        @SerializedName("id")@Expose private String user_id;
        @SerializedName("phoneNumber")@Expose private String phoneNumber;
        @SerializedName("pw")@Expose private String pw;
        @SerializedName("nickname")@Expose private String nickName;
        @SerializedName("profileImagePath") @Expose private String profileImagePath;
        @SerializedName("email")@Expose private String email;
        @SerializedName("birthDay")@Expose private String birthDay;
        @SerializedName("ageRange")@Expose private String ageRange;
        @SerializedName("userType")@Expose private String userType;

            public String getPw(){
                return pw;
            }

            public void setPw(String pw) {
                this.pw = pw;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public void setProfileImagePath(String profileImagePath) {
                this.profileImagePath = profileImagePath;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setBirthDay(String birthDay) {
                this.birthDay = birthDay;
            }

            public void setAgeRange(String ageRange) {
                this.ageRange = ageRange;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getUserId() {
                return user_id;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public String getNickName() {
                return nickName;
            }

            public String getProfileImagePath() {
                return profileImagePath;
            }

            public String getEmail() {
                return email;
            }

            public String getBirthDay() {
                return birthDay;
            }

            public String getAgeRange() {
                return ageRange;
            }

            public String getUserType() {
                return userType;
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
