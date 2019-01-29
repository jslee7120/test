package yaksok.dodream.com.yaksok;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

public class SettingPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);


        Button auto_cancel = (Button)findViewById(R.id.auto_cancel);
        auto_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoginActivity.editor.putBoolean("auto",false);
                LoginActivity.editor.remove("id");
                LoginActivity.editor.remove("pw");
                LoginActivity.editor.remove("userType");
                LoginActivity.editor.apply();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });



        Button kakao_logout = (Button)findViewById(R.id.kakao_logout);
        kakao_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        LoginActivity.editor.remove("id");
                        LoginActivity.editor.remove("pw");
                        LoginActivity.editor.remove("userType");
                        LoginActivity.editor.apply();
                    }
                });
            }
        });

        final Button naver_logout = (Button)findViewById(R.id.naver_logout);
        naver_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAuthLogin.getInstance().logout(getApplicationContext());
                LoginActivity.editor.remove("id");
                LoginActivity.editor.remove("pw");
                LoginActivity.editor.remove("userType");
                LoginActivity.editor.apply();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();


        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.activity_customize_actionbar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar)actionbar.getParent();
        parent.setContentInsetsAbsolute(0,0);

        return true;
    }
}
