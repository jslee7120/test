package yaksok.dodream.com.yaksok.login_kakao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.AccessTokenCallback;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.Get_pn;
import yaksok.dodream.com.yaksok.vo.BodyVO;
import yaksok.dodream.com.yaksok.LoginActivity;
import yaksok.dodream.com.yaksok.vo.SnsLogVO;
import yaksok.dodream.com.yaksok.MainPageActivity;

import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.login_user_info.Kakao_User_Info;
import yaksok.dodream.com.yaksok.vo.UserVO;


public class KakaoSignUp extends Activity {
    public Kakao_User_Info kakaoUser_info;
    private Gender user_gender;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    public UserService userService;
    SnsLogVO snsLogVO;
    Retrofit retrofit;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);


    }

    private void requestMe() {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");
        keys.add("kakao_account.birthday");
        keys.add("kakao_account.gender");
        keys.add("kakao_account.gender");
        keys.add("kakao_account.age_range");



        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onSuccess(MeV2Response result) {


                OptionalBoolean gender = result.getKakaoAccount().hasGender();
                if(gender == OptionalBoolean.FALSE){
                    handleScopeError(result.getKakaoAccount());
                    kakaoUser_info = new Kakao_User_Info();
                    kakaoUser_info.setUser_type("K");
                    kakaoUser_info.setNickname(result.getNickname());
                    kakaoUser_info.setProfileImagePath(result.getProfileImagePath());
                    kakaoUser_info.setEmail(result.getKakaoAccount().getEmail());
                    kakaoUser_info.setBirth(result.getKakaoAccount().getBirthday());
                    kakaoUser_info.setId(result.getId());
                    kakaoUser_info.setAgeRange(result.getKakaoAccount().getAgeRange());

                    Log.i("nickname",result.getNickname());
                    Log.i("profile_img",result.getProfileImagePath());
                    Log.i("email",result.getKakaoAccount().getEmail());
                    Log.i("birthday",kakaoUser_info.getBirth());
                    Log.i("id",""+result.getId());
                    Log.i("arange",""+kakaoUser_info.getK_age());
                    Log.i("userType",""+kakaoUser_info.getUser_type());

                    LoginActivity.userType = kakaoUser_info.getUser_type();
//                    LoginActivity.editor.putString("id",String.valueOf(kakaoUser_info.getId()));
//                    LoginActivity.editor.putString("userType",LoginActivity.userType);
//                    LoginActivity.editor.apply();
                     kakaToLogin(String.valueOf(kakaoUser_info.getId()),kakaoUser_info.getUser_type());



                }




            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
            }


            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }



        });


        }

    private void handleScopeError(final UserAccount account) {
        List<String> neededScopes = new ArrayList<>();

        if (account.needsScopeGender()) {
            neededScopes.add("gender");

        }
        Session.getCurrentSession().updateScopes(this, neededScopes, new
                AccessTokenCallback() {
                    @Override
                    public void onAccessTokenReceived(AccessToken accessToken) {
                        // 유저에게 성공적으로 동의를 받음. 토큰을 재발급 받게 됨.
                        user_gender = account.getGender();


                    }

                    @Override
                    public void onAccessTokenFailure(ErrorResult errorResult) {
                        // 동의 얻기 실패
                    }
                });
    }
    public void kakaToLogin(String id, String userType){
        UserVO snsLogVO = new UserVO();

        snsLogVO.setId(String.valueOf(Kakao_User_Info.id));
        snsLogVO.setUserType(kakaoUser_info.getUser_type());
        LoginActivity.userType = "K";



        Call<BodyVO> snsBodyVOCall = userService.postSnsLogin(snsLogVO);
        snsBodyVOCall.enqueue(new Callback<BodyVO>() {
            @Override
            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                BodyVO snsBodyVO = response.body();

                if (snsBodyVO.getStatus().equals("200")) {
                    Toast.makeText(getApplicationContext(), "로그인 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                    Toast.makeText(getApplicationContext(),"반갑습니다.",Toast.LENGTH_LONG).show();

                    if(LoginActivity.loginInformation.getBoolean("auto",true)){
                        LoginActivity.editor.putString("id",String.valueOf(kakaoUser_info.getId()));
                        LoginActivity.editor.putString("pw",String.valueOf(kakaoUser_info.getUser_type()));
                    }else{
                        
                    }

                } else if (snsBodyVO.getStatus().equals("024")) {
                    Toast.makeText(getApplicationContext(), "유저 타입이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Get_pn.class));
                } else if (snsBodyVO.getStatus().equals("500")) {
                    Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BodyVO> call, Throwable t) {
                redirectLoginActivity();
            }
        });
    }







    private void redirectMainActivity() {


        LoginActivity.userType = kakaoUser_info.getUser_type();
       finish();



    }

    private void redirectLoginActivity() {
        final Intent intent = new Intent(this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();

    }
}
