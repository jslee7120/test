package yaksok.dodream.com.yaksok;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.js.MedicineVOList;
import yaksok.dodream.com.yaksok.js.SearchPill;
import yaksok.dodream.com.yaksok.js.StatusVO;
import yaksok.dodream.com.yaksok.service.UserService;

public class Alarm_On extends Activity {
    Intent intent = getIntent();
    String userId,pillNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.alarm_on);

        Button bt_Ok = (Button) findViewById(R.id.bt_D_Ok);
        Button bt_Cancle = (Button)findViewById(R.id.bt_D_Cancel);

        Log.d("다이얼로그","약 먹으세요 들어옴");

//        Log.d("제발",intent.getStringExtra("user"));

       // userId = intent.getStringExtra("user");
       // pillNo = intent.getStringExtra("pill");

        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPageActivity.n = 1;
                Intent intent1 = new Intent(getApplicationContext(), TakeMedicineDialog.class);
                intent1.putExtra("uId",userId);
                intent1.putExtra("pNo",pillNo);
              //  startActivityForResult(intent1,7000);
                startActivity(intent1);
            }
        });

        bt_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
