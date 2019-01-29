package yaksok.dodream.com.yaksok;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.login_kakao.KakaoSignUp;
import yaksok.dodream.com.yaksok.login_user_info.Kakao_User_Info;
import yaksok.dodream.com.yaksok.login_user_info.Naver_User_Info;
import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.vo.BodyVO;
import yaksok.dodream.com.yaksok.vo.UserVO;

public class Sign_up_with_sns extends AppCompatActivity {

    LoginButton main_kakao_btn;
    private final int REQUEST_CODE = 1000;
    private static OAuthLogin oAuthLogin;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private static String OAUTH_CLIENT_ID = "UoO6ax8Kj1TRjRwr8vHf";
    private static String OAUTH_CLIENT_SECRET = "mueA5IlnkV";
    private static String OAUTH_CLIENT_NAME = "gam7325";
    private OAuthLoginButton login_naver_btn;
    public Map<String, String> mUserInfoMap;
    public Naver_User_Info naver_user_info;

    private static String id;
    private static String name;
    private static String profile_path;
    private static String email;
    private static String birthday;
    private static String age_range;

    public LinearLayout get_phone_number_layout;
    public UserVO userVO;
    public Retrofit retrofit;
    public UserService userService;

    SessionCallback callback;


    //레이아웃 뷰들
    EditText sign_up_with_sns_id,sign_up_with_sns_pw,sign_up_with_sns_pn;
    Button sign_up_with_sns_complete_btn;
    boolean isVaildId = false,isVaildPw = false,isVaildPn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_sns);

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        callback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(callback);


        @SuppressLint("HandlerLeak") OAuthLoginHandler oAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {

             /*   String refreshToken = oAuthLogin.getRefreshToken(mContext);
                long expiresAt = oAuthLogin.getExpiresAt(mContext);
                String tokenType = oAuthLogin.getTokenType(mContext);
                Log.d("asdf11",accessToken);
                Log.d("asdf22",refreshToken);
                Log.d("asdf33",String.valueOf(expiresAt));
                Log.d("asdf44",tokenType);
                Log.d("asdf55", oAuthLogin.getState(mContext).toString());
*/
                    new Thread() {
                        @Override
                        public void run() {
                            final String accessToken = oAuthLogin.getAccessToken(mContext);
                            String data = oAuthLogin.requestApi(mContext, accessToken, "https://openapi.naver.com/v1/nid/me");

                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                id = jsonObject.getJSONObject("response").getString("id");
                                name = jsonObject.getJSONObject("response").getString("name");
                                profile_path = jsonObject.getJSONObject("response").getString("profile_image");
                                email = jsonObject.getJSONObject("response").getString("email");
                                birthday = jsonObject.getJSONObject("response").getString("birthday");
                                age_range = jsonObject.getJSONObject("response").getString("age");


                                naver_user_info = new Naver_User_Info();
                                naver_user_info.setUser_type("N");
                                naver_user_info.setNaver_id(id);
                                naver_user_info.setNaver_name(name);
                                naver_user_info.setNaver_profile_path(profile_path);
                                naver_user_info.setNaver_email(email);
                                naver_user_info.setNaver_birthday(birthday);
                                naver_user_info.setNaver_age_range(age_range);
                                LoginActivity.userType = naver_user_info.getUser_type();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();



                }
            }
        };



        mContext = this;
        init();
        login_naver_btn = (OAuthLoginButton) findViewById(R.id.login_naver_btn);
        login_naver_btn.setOAuthLoginHandler(oAuthLoginHandler);

        main_kakao_btn = (LoginButton) findViewById(R.id.login_kakao_btn);
        main_kakao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.kakao.auth.Session session = com.kakao.auth.Session.getCurrentSession();
                session.addCallback(callback);
                session.open(AuthType.KAKAO_LOGIN_ALL, Sign_up_with_sns.this);

                LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = LoginActivity.loginInformation.edit();



            }
        });

        sign_up_with_sns_id = (EditText)findViewById(R.id.using_sns_id_edt);
        sign_up_with_sns_pw = (EditText)findViewById(R.id.using_sns_pw_edt);
        sign_up_with_sns_pn = (EditText)findViewById(R.id.using_sns_phone_pn_edt);
        sign_up_with_sns_complete_btn = (Button)findViewById(R.id.using_sns_complete_btn);

        sign_up_with_sns_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidPhoneNumber(sign_up_with_sns_pn.getText().toString()) && isValidId(sign_up_with_sns_id.getText().toString()) && isValidPw(sign_up_with_sns_pw.getText().toString())){
                    if(LoginActivity.userType.equals("K")){
                        kakaoUserInfoToServer();
                    }
                    else if(LoginActivity.userType.equals("N")){
                        naverUserInfoToServer();
                    }
                }
                else if(isValidId(sign_up_with_sns_id.getText().toString()) && isValidPw(sign_up_with_sns_pw.getText().toString())){
                    Toast.makeText(getApplicationContext(),"전화 번호가 유효하지 않습니다.",Toast.LENGTH_SHORT).show();
                    sign_up_with_sns_pn.setText("");
                    sign_up_with_sns_pn.setFocusable(true);
                }
                else if(isValidPhoneNumber(sign_up_with_sns_pn.getText().toString()) && isValidId(sign_up_with_sns_id.getText().toString())){
                    Toast.makeText(getApplicationContext(),"비밀번호가 유효하지 않습니다.",Toast.LENGTH_SHORT).show();
                    sign_up_with_sns_pw.setText("");
                    sign_up_with_sns_pw.setFocusable(true);
                }
                else if(isValidPhoneNumber(sign_up_with_sns_pn.getText().toString()) && isValidPw(sign_up_with_sns_pw.getText().toString())){
                    Toast.makeText(getApplicationContext(),"아이디가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                    sign_up_with_sns_id.setText("");
                    sign_up_with_sns_id.setFocusable(true);
                }
                else{
                    sign_up_with_sns_id.setText("");
                    sign_up_with_sns_pw.setText("");
                    sign_up_with_sns_pn.setText("");
                }
            }
        });
    }


    public void init() {
        oAuthLogin = OAuthLogin.getInstance();
        oAuthLogin.showDevelopersLog(true);
        oAuthLogin.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }


    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {

            return;
        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignUp();


        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {

            if (exception != null) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
            setContentView(R.layout.activity_sign_up_with_sns);

        }

    }


    protected void redirectSignUp() {
        final Intent intent = new Intent(this, KakaoSignUp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);

    }

    public static boolean isValidPhoneNumber(String phoneNumber) {

        boolean returnValue = false;

        String regex = "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";


        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(phoneNumber);

        if (m.matches()) {
            returnValue = true;


        }

        return returnValue;

    }
   public static boolean isValidId(String id){
        boolean returnValue = false;

       String regex = "^[a-zA-Z0-9]{6,10}$";


       Pattern p = Pattern.compile(regex);

       Matcher m = p.matcher(id);

       if (m.matches()) {
           returnValue = true;

       }

       return returnValue;

   }
   public static boolean isValidPw(String pw){
       boolean returnValue = false;

       String regex = "^[a-zA-Z0-9!@.#$%^&*?_~]{7,12}$";


       Pattern p = Pattern.compile(regex);

       Matcher m = p.matcher(pw);

       if (m.matches()) {
           returnValue = true;

       }

       return returnValue;


   }





    //equest  ( userType = K, N)
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

    //code
    //201 : OK
    //400 : 잘못된요청(userType)
    //403 : 아이디중복
    //409 : 입력된 핸드폰번호로 가입된 계정 존재
    //500 : Server Error

    public void naverUserInfoToServer() {
        userVO = new UserVO();
        userVO.setId(naver_user_info.getNaver_id());
        userVO.setPhoneNumber(naver_user_info.getPhone_number());
        userVO.setNickname(naver_user_info.getNaver_name());
        userVO.setProfileImagePath(naver_user_info.getNaver_profile_path());
        userVO.setEmail(naver_user_info.getNaver_email());
        userVO.setBirthday(naver_user_info.getNaver_birthday());
        userVO.setAgeRange(naver_user_info.getNaver_age_range());
        userVO.setUserType(naver_user_info.getUser_type());

        Call<BodyVO> bodyVOCall = userService.postSignUp(userVO);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                System.out.println(bodyVO);

                if (bodyVO.getStatus().equals("201")) {
                    Toast.makeText(getApplicationContext(), "회원 가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                    LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = LoginActivity.loginInformation.edit();
                    editor.putString("id", sign_up_with_sns_id.getText().toString());
                    editor.putString("pw",sign_up_with_sns_pw.getText().toString());
                    editor.putString("type", LoginActivity.userType);
                    editor.putBoolean("auto", true);
                    editor.apply();//이 부분이 있어야 저장이

                    UserInfo.setUserid(id);
                    startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                } else if (bodyVO.getStatus().equals("400")) {
                    Toast.makeText(getApplicationContext(), "유저 타입이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                } else if (bodyVO.getStatus().equals("409")) {
                    Toast.makeText(getApplicationContext(), "입력된 휴대 전화 번호가 중복됩니다.", Toast.LENGTH_SHORT).show();

                } else if (bodyVO.getStatus().equals("500")) {
                    Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }

    //equest  ( userType = K, N)
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


    public void kakaoUserInfoToServer() {
        userVO = new UserVO();

        Kakao_User_Info kakao_user_info2 = new Kakao_User_Info();
        System.out.println(kakao_user_info2.getBirth());
        userVO.setId(String.valueOf(Kakao_User_Info.id));
        userVO.setPw(sign_up_with_sns_pw.getText().toString());
        userVO.setPhoneNumber(Kakao_User_Info.phoneNumber);
        userVO.setNickname(Kakao_User_Info.nickname);
        userVO.setProfileImagePath(Kakao_User_Info.profileImagePath);
        userVO.setEmail(Kakao_User_Info.email);
        userVO.setBirthday(kakao_user_info2.getBirth().substring(0, 2) + "-" + kakao_user_info2.getBirth().substring(2));
        userVO.setAgeRange(kakao_user_info2.getK_age());
        userVO.setUserType(Kakao_User_Info.user_type);



        Call<BodyVO> voCall = userService.postSignUp(userVO);
        voCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();

                if (bodyVO.getStatus().equals("201")) {
                    Toast.makeText(getApplicationContext(), "회원 가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = LoginActivity.loginInformation.edit();
                    editor.putString("id", sign_up_with_sns_id.getText().toString());
                    editor.putString("pw",sign_up_with_sns_pw.getText().toString());
                    editor.putString("type", LoginActivity.userType);
                    editor.putBoolean("auto", true);
                    editor.apply();//이 부분이 있어야 저장이
                    UserInfo.setUserid(String.valueOf(Kakao_User_Info.id));

                    startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                    Toast.makeText(getApplicationContext(),"반갑습니다.",Toast.LENGTH_LONG).show();

                } else if (bodyVO.getStatus().equals("400")) {
                    Toast.makeText(getApplicationContext(), "유저 타입이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                } else if (bodyVO.getStatus().equals("409")) {
                    Toast.makeText(getApplicationContext(), "입력된 휴대 전화 번호가 중복됩니다.", Toast.LENGTH_SHORT).show();

                } else if (bodyVO.getStatus().equals("500")) {
                    Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);

        boolean auto_login = LoginActivity.loginInformation.getBoolean("auto", true);

        if (auto_login) {
            startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
        }

    }
}
