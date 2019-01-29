package yaksok.dodream.com.yaksok.login_user_info;

public class General_User_Info {
    private String general_name, profile_path,email,birthday;

    public General_User_Info(String general_name, String profile_path, String email, String birthday) {
        this.general_name = general_name;
        this.profile_path = profile_path;
        this.email = email;
        this.birthday = birthday;
    }

    public String getGeneral_name() {
        return general_name;
    }

    public void setGeneral_name(String general_name) {
        this.general_name = general_name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
