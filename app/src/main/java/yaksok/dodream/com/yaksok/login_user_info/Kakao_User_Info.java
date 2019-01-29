package yaksok.dodream.com.yaksok.login_user_info;

import com.kakao.usermgmt.response.model.AgeRange;
import com.kakao.usermgmt.response.model.Gender;

import java.util.Map;

public class Kakao_User_Info {

//    public static String nickname;
//    public static String profileImagePath;
//    private static String thumnailPath;
//    private static long id;
//    private static String birth;
//    private static Gender gender;
//    private static AgeRange ageRange;
//    public static String email;
    public static String user_type;
    public static String profileImagePath;
    public static String thumnailPath;
    public static long id;
    public static String birth;
    public static AgeRange ageRange;
    public static String email;
    public static String phoneNumber;
    public static String nickname;


    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public AgeRange getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(AgeRange ageRange) {
        this.ageRange = ageRange;
    }


    //1029
    public String getBirth() {
        return  birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getK_age() {
        return  String.valueOf(ageRange).substring(4,9).replace("_","-");
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
