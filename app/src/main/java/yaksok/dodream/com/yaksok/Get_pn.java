package yaksok.dodream.com.yaksok;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.login_user_info.Kakao_User_Info;
import yaksok.dodream.com.yaksok.login_user_info.Naver_User_Info;
import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.vo.BodyVO;
import yaksok.dodream.com.yaksok.vo.UserVO;

public class Get_pn extends AppCompatActivity {
    Retrofit retrofit;
    UserService userService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pn);

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);


        final EditText get_pn_edt = (EditText) findViewById(R.id.get_pn_edt);
        Button get_pn_ok_btn = (Button) findViewById(R.id.get_pn_btn);

        get_pn_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //“id” : “…” NOT NULL
                //	“phoneNumber” : ”…“ NOT NULL
                //	“nickname” : ”…“ NOT NULL
                //	“profileImagePath” : “…”
                //	“email” : “…”
                // 	“birthday” : “…”
                //	“ageRange” : “…”
                //	“userType”:”…”	  NOT NULL   char형

                if (LoginActivity.userType.equals("K")) {
                    LoginActivity.userVO = new UserVO();
                    Kakao_User_Info kakao_user_info = new Kakao_User_Info();
                    LoginActivity.userVO.setId(String.valueOf(Kakao_User_Info.id));
                    LoginActivity.userVO.setPhoneNumber(get_pn_edt.getText().toString());
                    LoginActivity.userVO.setNickname(Kakao_User_Info.nickname);
                    LoginActivity.userVO.setProfileImagePath(Kakao_User_Info.profileImagePath);
                    LoginActivity.userVO.setEmail(Kakao_User_Info.email);
                    LoginActivity.userVO.setBirthday(Kakao_User_Info.birth.substring(0, 2) + "-" + Kakao_User_Info.birth.substring(2));
                    LoginActivity.userVO.setAgeRange(kakao_user_info.getK_age());
                    LoginActivity.userVO.setUserType(kakao_user_info.getUser_type());

                    Call<BodyVO> bodyVOCall = userService.postSignUp(LoginActivity.userVO);
                    bodyVOCall.enqueue(new Callback<BodyVO>() {
                        @SuppressLint("CommitPrefEdits")
                        @Override
                        public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                            BodyVO bodyVO = response.body();
                            System.out.println(bodyVO);

                            if (bodyVO.getStatus().equals("201")) {
                                if (LoginActivity.checkBox.isSelected()) {//자동로그인 정보 저장

                                    LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);
                                    LoginActivity.editor = LoginActivity.loginInformation.edit();
                                    LoginActivity.editor.putString("id", Naver_User_Info.getNaver_id());
                                    LoginActivity.editor.putString("type", LoginActivity.userType);
                                    LoginActivity.editor.apply();//이 부분이 있어야 저장이
                                }
                                Toast.makeText(getApplicationContext(), "회원 가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AddYourFmaily.class));

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
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

                if (LoginActivity.userType.equals("N")) {
                    LoginActivity.userVO = new UserVO();
                    LoginActivity.userVO.setId(Naver_User_Info.getNaver_id());
                    LoginActivity.userVO.setPhoneNumber(get_pn_edt.getText().toString());
                    LoginActivity.userVO.setNickname(Naver_User_Info.getNaver_name());
                    LoginActivity.userVO.setProfileImagePath(Naver_User_Info.getNaver_profile_path());
                    LoginActivity.userVO.setEmail(Naver_User_Info.getNaver_email());
                    LoginActivity.userVO.setBirthday(Naver_User_Info.getNaver_birthday());
                    LoginActivity.userVO.setAgeRange(Naver_User_Info.getNaver_age_range());
                    LoginActivity.userVO.setUserType(Naver_User_Info.getUser_type());

                    Call<BodyVO> bodyVOCall2 = userService.postSignUp(LoginActivity.userVO);
                    bodyVOCall2.enqueue(new Callback<BodyVO>() {
                        @SuppressLint("CommitPrefEdits")
                        @Override
                        public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                            BodyVO bodyVO = response.body();
                            System.out.println(bodyVO);

                            if (bodyVO.getStatus().equals("201")) {
                                Toast.makeText(getApplicationContext(), "회원 가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                                if (LoginActivity.checkBox.isSelected()) {//자동로그인 정보 저장

                                    LoginActivity.loginInformation = getSharedPreferences("user", MODE_PRIVATE);
                                    LoginActivity.editor = LoginActivity.loginInformation.edit();
                                    LoginActivity.editor.putString("id", Naver_User_Info.getNaver_id());
                                    LoginActivity.editor.putString("pw", "");
                                    LoginActivity.editor.putString("type", LoginActivity.userType);
                                    LoginActivity.editor.apply();//이 부분이 있어야 저장이
                                }

                                startActivity(new Intent(getApplicationContext(), AddYourFmaily.class));

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

            }
        });
    }
}


