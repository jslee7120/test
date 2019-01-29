package yaksok.dodream.com.yaksok;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
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
import yaksok.dodream.com.yaksok.vo.FcmTokenVO;
import yaksok.dodream.com.yaksok.vo.SnsLogVO;
import yaksok.dodream.com.yaksok.vo.UserVO;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static UserService userService = null;
    LoginButton main_kakao_btn;
    private final int REQUEST_CODE = 1000;
    private static OAuthLogin oAuthLogin;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private static String OAUTH_CLIENT_ID = "UoO6ax8Kj1TRjRwr8vHf";
    private static String OAUTH_CLIENT_SECRET = "mueA5IlnkV";
    private static String OAUTH_CLIENT_NAME = "gam7325";
    private OAuthLoginButton login_naver_btn;
    public Naver_User_Info naver_user_info;

    private static String id;
    private static String name;
    private static String profile_path;
    private static String email;
    private static String birthday;
    private static String age_range;

    public LinearLayout get_phone_number_layout;


    private final int NUMBER = 12345;

    public static String userType;
    SessionCallback callback;


    private EditText using_pn_edt, using_verfied_num_edt;
    private Button using_pn_btn, using_confirm_btn, using_next_btn;
    private boolean pnIsOk, numIsOk;
    KakaoSignUp kakaoSignUp;
    private Kakao_User_Info kakao_user_info;
    private Retrofit retrofit;
    private Button login_normal_btn, login_sign_up_btn, using_sns_for_login_btn;

    public static SharedPreferences loginInformation;
    public static SharedPreferences.Editor editor;

    public static EditText main_id_edt;
    EditText main_pw_edt;


    public static String user_pnumber = "";

    public static UserVO userVO;
    public Button sign_with_sns_btn;
    public static CheckBox checkBox;
    public String auto_id;
    public String auto_pw;
    public String auto_type;
    public boolean auto_quustion;
    public static boolean autologin;

    ImageView fake_g_login,fake_sign_up,fake_n_login,fake_k_login;

    public static SnsLogVO snsLogVO;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        callback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(callback);


        main_id_edt = (EditText) findViewById(R.id.main_id_edt);
        main_pw_edt = (EditText) findViewById(R.id.main_pw_edt);
        fake_g_login = (ImageView)findViewById(R.id.fake_g_login);
        fake_sign_up = (ImageView)findViewById(R.id.fake_sign_up);
       fake_n_login = (ImageView)findViewById(R.id.fake_id_n_login);
        fake_k_login = (ImageView)findViewById(R.id.fake_k_login);

        fake_g_login.setOnClickListener(this);
        fake_sign_up.setOnClickListener(this);
       fake_n_login.setOnClickListener(this);
        fake_k_login.setOnClickListener(this);



        loginInformation = getSharedPreferences("user",MODE_PRIVATE);
        editor = loginInformation.edit();



        checkBox = (CheckBox)findViewById(R.id.setAutoLogin_check);
        checkBox.setChecked(loginInformation.getBoolean("auto",true));
        editor.putBoolean("auto", checkBox.isChecked());
        editor.apply();
        if(loginInformation.getBoolean("auto",true)){
            switch (loginInformation.getString("userType","")){
                case "G":
                    String test_id = loginInformation.getString("id","");
                    String test_pw = loginInformation.getString("pw","");
                    g_login(test_id,test_pw,"G");
                    break;
                case "N":
                    String naver_id = loginInformation.getString("id","");
                    String naver_type = "N";
                    naverToLogin(naver_id,naver_type);
                    break;
                case "K":
                    String kakao_id = loginInformation.getString("id","");
                    String kakao_type = loginInformation.getString("userType","");
                    kakaToLogin(kakao_id,kakao_type);
                    break;
            }

        }
        else{
            checkBox.setChecked(false);
        }


        Toast.makeText(getApplicationContext(),loginInformation.getString("id","")+""+loginInformation.getString("userType",""),Toast.LENGTH_LONG).show();



        login_normal_btn = (Button) findViewById(R.id.login_normal_btn);
            login_normal_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userType = "G";
                    editor.putString("userType",userType);
                    editor.apply();

                   // Toast.makeText(getApplicationContext(),loginInformation.getString("id","")+loginInformation.getString("pw","")+loginInformation.getString("userType",""),Toast.LENGTH_LONG).show();

//                    main_id_edt.setText(loginInformation.getString("id",""));
//                    main_pw_edt.setText(loginInformation.getString("pw",""));
                   // Log.d("check",loginInformation.getString("id","")+""+loginInformation.getString("pw","")+""+loginInformation.getString("userType",""));

                  g_login(main_id_edt.getText().toString(),main_pw_edt.getText().toString(),"G");
//                    userVO = new UserVO();
//                    userVO.setId(main_id_edt.getText().toString());
//                    userVO.setPw(main_pw_edt.getText().toString());
//                    userVO.setUserType("G");
//
//
//
//                    Log.d("@@@@@@@@@@@@@@@",userVO.getId()+"pw"+""+userVO.getPw()+""+userVO.getUserType());
//                    Call<BodyVO> bodyVOCall = userService.postGneralLogin(userVO);
//                    bodyVOCall.enqueue(new Callback<BodyVO>() {
//                        @Override
//                        public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
//                            BodyVO bodyVO = response.body();
//                            System.out.println("glogin실행됨"+bodyVO.getStatus()+userVO.getUserType()+userVO.getId()+userVO.getPw());
//                            Toast.makeText(getApplicationContext(), userVO.getId()+""+userVO.getPw()+""+userVO.getUserType(), Toast.LENGTH_SHORT).show();
//                            if(bodyVO.getStatus().equals("200")){
//                                Toast.makeText(getApplicationContext(),"로그인 성공 되었습니다.",Toast.LENGTH_LONG).show();
//
//                                startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
//
//                                finish();
//                            }
//                            else if (bodyVO.getStatus().equals("024")) {
//                                Toast.makeText(getApplicationContext(), "잘못된 요청", Toast.LENGTH_SHORT).show();
//                            }
//                            else if (bodyVO.getStatus().equals("400")) {
//                                Toast.makeText(getApplicationContext(), "아이디 중복", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "아이디 중복", Toast.LENGTH_SHORT).show();
//                            }
//
//                            else if (bodyVO.getStatus().equals("500")) {
//                                Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<BodyVO> call, Throwable t) {
//                            System.out.println("@@@@@@@@@@@@@@"+t.getMessage());
//                        }
//                    });








                }
            });

        login_sign_up_btn = (Button) findViewById(R.id.login_sign_up_btn);
        login_sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));

            }
        });



        @SuppressLint("HandlerLeak") OAuthLoginHandler oAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {


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
                                userType = "N";

                                editor.putString("userType",userType);
                                editor.apply();
                                naverToLogin(id,userType);




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
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);

                LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = LoginActivity.loginInformation.edit();



            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putString("id",main_id_edt.getText().toString());
                    editor.putString("pw",main_pw_edt.getText().toString());
                    editor.putBoolean("auto",true);
                    editor.apply();
                    checkBox.setChecked(loginInformation.getBoolean("auto",true));
                    Log.d("save",loginInformation.getString("id","")+""+loginInformation.getString("pw","")+""+loginInformation.getBoolean("auto",true));
                }else{
                    editor.putBoolean("auto",false);
                    editor.apply();
                    checkBox.setChecked(loginInformation.getBoolean("auto",false));

                    Log.d("unsave",""+loginInformation.getBoolean("auto",false));
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



//        if (loginInformation.getBoolean("auto",true)){
//            g_login(loginInformation.getString("id",""),loginInformation.getString("pw",""),loginInformation.getString("userType",""));
//        }





//        loginInformation = getSharedPreferences("user", MODE_PRIVATE);
//        boolean isAuto = loginInformation.getBoolean("auto",checkBox.isSelected());
//        String auto_id = loginInformation.getString("id", "");
//        String auto_pw = loginInformation.getString("pw","");





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


//response
//BODY{
//	“status” : “code”
//}
//
//code
//200 : OK
//024 : 인증실패(로그인실패)
//400 : 잘못된요청(userType)
//500 : Server Error
    public void naverToLogin(String id, String userType){
        userVO = new UserVO();
        userVO.setId(id);
        userVO.setUserType(userType);
//        editor.putString("id",snsLogVO.getId());
//        editor.putString("userType",snsLogVO.getUserType());
//        editor.apply();



        Call<BodyVO> snsBodyVOCall = userService.postSnsLogin(userVO);
        snsBodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO snsBodyVO = response.body();
                //System.out.println("id"+snsLogVO.getId()+"user"+snsLogVO.getUserType()+"sns"+snsBodyVO.getStatus());
                if (snsBodyVO.getStatus().equals("200")) {
                    pushToken();
                    Toast.makeText(getApplicationContext(), "로그인 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                    Toast.makeText(getApplicationContext(),"반갑습니다.",Toast.LENGTH_LONG).show();

                } else if (snsBodyVO.getStatus().equals("024")) {
                    Toast.makeText(getApplicationContext(), "유저 타입이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Get_pn.class));
                } else if (snsBodyVO.getStatus().equals("500")) {
                    Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }



    public void g_login(String g_id, String g_pw,String userType){

         userVO = new UserVO();
         userVO.setId(g_id);
         userVO.setPw(g_pw);
         userVO.setUserType("G");


        Call<BodyVO> bodyVOCall = userService.postGneralLogin(userVO);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO bodyVO = response.body();
                System.out.println("glogin실행됨"+bodyVO.getStatus()+userVO.getUserType()+userVO.getId()+userVO.getPw());
                Toast.makeText(getApplicationContext(), userVO.getId()+""+userVO.getPw()+""+userVO.getUserType(), Toast.LENGTH_SHORT).show();
                if(bodyVO.getStatus().equals("200")){
                    Toast.makeText(getApplicationContext(),"로그인 성공 되었습니다.",Toast.LENGTH_LONG).show();
                    pushToken();
                    startActivity(new Intent(getApplicationContext(),MainPageActivity.class));


                }
                else if (bodyVO.getStatus().equals("024")) {
                    Toast.makeText(getApplicationContext(), "잘못된 요청", Toast.LENGTH_SHORT).show();
                }
                else if (bodyVO.getStatus().equals("400")) {
                    Toast.makeText(getApplicationContext(), "아이디 중복", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "아이디 중복", Toast.LENGTH_SHORT).show();
                }

                else if (bodyVO.getStatus().equals("500")) {
                    Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                System.out.println("@@@@@@@@@@@@@@"+t.getMessage());
            }
        });

    }
    public void kakaToLogin(String id, String userType){
        userVO = new UserVO();
        userVO.setId(id);
        userVO.setUserType(userType);

        Call<BodyVO> snsBodyVOCall = userService.postSnsLogin(userVO);
        snsBodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO snsBodyVO = response.body();

                if (snsBodyVO.getStatus().equals("200")) {
                    pushToken();
                    startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                    Toast.makeText(getApplicationContext(),"반갑습니다.",Toast.LENGTH_LONG).show();

                } else if (snsBodyVO.getStatus().equals("024")) {
                    Toast.makeText(getApplicationContext(), "유저 타입이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Get_pn.class));
                } else if (snsBodyVO.getStatus().equals("500")) {
                    Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fake_g_login:
                login_normal_btn.performClick();
                break;
            case R.id.fake_sign_up:
                login_sign_up_btn.performClick();
                break;
            case R.id.fake_id_n_login:
                login_naver_btn.performClick();
               break;
            case R.id.fake_k_login:
                main_kakao_btn.performClick();
                break;

        }

    }
    public void pushToken(){
        FcmTokenVO fcmTokenVO = new FcmTokenVO();
        fcmTokenVO.setId(userVO.getId());
        fcmTokenVO.setFcmToken(FirebaseInstanceId.getInstance().getToken());

        Call<BodyVO> bodyVOCall = userService.putToken(fcmTokenVO);
        bodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(@NonNull Call<BodyVO> call, @NonNull Response<BodyVO> response) {
                Log.d("user_token",response.message());
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {

            }
        });
    }

}





