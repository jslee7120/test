package yaksok.dodream.com.yaksok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.LoginActivity;
import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.js.InsertPillScroll;
import yaksok.dodream.com.yaksok.js.InsertPillScrollQR;
import yaksok.dodream.com.yaksok.js.ListViewAdapter;
import yaksok.dodream.com.yaksok.js.MyMedicineResponseTypeVO;
import yaksok.dodream.com.yaksok.service.UserService;

public class InsertPillActivity extends AppCompatActivity{

    Button bt_Insert, bt_QRScan;
    private IntentIntegrator qrScan;
    String qrResult;

    Retrofit retrofit;
    UserService userService;

    ListView lv_MyPill;
    ListViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertpill);

        bt_Insert = (Button)findViewById(R.id.bt_Insert);
        bt_QRScan = (Button)findViewById(R.id.bt_QRScan);
        lv_MyPill = (ListView)findViewById(R.id.lv_MyPill);

        adapter = new ListViewAdapter();
        lv_MyPill.setAdapter(adapter);

        qrScan = new IntentIntegrator(this);

        bt_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertPillScroll.class);
                startActivityForResult(intent,5000);
            }
        });


        //QR코드 스캔
        bt_QRScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scan option
                qrScan.setPrompt("QR코드를 스캔하세요");
                qrScan.setOrientationLocked(false); //디폴트는 가로인데 세로일 경우 세로로 바꾸는 함
                qrScan.initiateScan();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        Call<MyMedicineResponseTypeVO> call = userService.getMymediciens(LoginActivity.userVO.getId());
        call.enqueue(new Callback<MyMedicineResponseTypeVO>() {
            @Override
            public void onResponse(Call<MyMedicineResponseTypeVO> call, Response<MyMedicineResponseTypeVO> response) {
                MyMedicineResponseTypeVO myMedicineResponseTypeVO = response.body();
                System.out.println("############" + myMedicineResponseTypeVO.getStatus());
                if (myMedicineResponseTypeVO.getStatus().equals("200")) {
                    for(int i=0; i<myMedicineResponseTypeVO.getResult().size(); i++) {
                        adapter.addItem(myMedicineResponseTypeVO.getResult().get(i).getName());
                    }
                } else if (myMedicineResponseTypeVO.getStatus().equals("204"))
                    Toast.makeText(getApplicationContext(), "등록된 약이 없습니다", Toast.LENGTH_LONG).show();
                else if (myMedicineResponseTypeVO.getStatus().equals("500"))
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MyMedicineResponseTypeVO> call, Throwable t) {

            }
        });

        Intent resultIntent = new Intent();
        setResult(4000,resultIntent);
    }




    //QR스캔후 정보를 파싱한다(해당 액티비티에서 QR스캐너 띄움)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(this, "스캔완료!", Toast.LENGTH_SHORT).show();
                System.out.println("@@@@@@@@@@@@@@@@@@@@Respone" + result.getContents());//  반환값 뭔지 확인
                qrResult = result.getContents();
                StringTokenizer tokens = new StringTokenizer(qrResult);
                Intent intent = new Intent(getApplicationContext(), InsertPillScrollQR.class);

                //QR로 찍은 정보 토큰으로 나누어 스트링에 저장 후 인텐트에 담아 다음 액티비티로 넘김
                String num1 = tokens.nextToken(",");
                String num2 = tokens.nextToken(",");
                String num3 = tokens.nextToken(",");
                String pill1 = tokens.nextToken(",");
                String pill2 = tokens.nextToken(",");
                String pill3 = tokens.nextToken(",");
                String dosagi = tokens.nextToken(",");
                intent.putExtra("num1",num1);
                intent.putExtra("num2",num2);
                intent.putExtra("num3",num3);
                intent.putExtra("pill1",pill1);
                intent.putExtra("pill2",pill2);
                intent.putExtra("pill3",pill3);
                intent.putExtra("dosagi",dosagi);
                startActivityForResult(intent,5000);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if(requestCode == 5000) {
            finish();
            startActivity(new Intent(this, InsertPillActivity.class));
        }

    }

}
