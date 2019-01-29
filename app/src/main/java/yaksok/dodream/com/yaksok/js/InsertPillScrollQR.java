package yaksok.dodream.com.yaksok.js;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.LoginActivity;
import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.service.UserService;

public class InsertPillScrollQR extends AppCompatActivity {
    Button bt_List_Add, bt_PillInsert, bt_1time, bt_2time, bt_3time;
    EditText et_Pill, et_dosagi, et_DiseaseName;
    LinearLayout lo_tab;
    TimePickerDialog dialog1,dialog2,dialog3;
    TextView tv_1h, tv_1m, tv_2h, tv_2m, tv_3h, tv_3m;
    int  i=0;
    List<String> time;
    List<Integer> pillList;
    String h, m;
    Button bt_add, bt_delete;
    Retrofit retrofit;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertself);

        Intent intent = getIntent();

        final LinearLayout inLayout = (LinearLayout) findViewById(R.id.lo_EditText);
        //추가 될 곳을 지정 -> R.layout.main 안에 지정

        pillList = new ArrayList<>();
        time =  new ArrayList<>();

        bt_List_Add = (Button) findViewById(R.id.bt_ListAdd);
        bt_PillInsert = (Button) findViewById(R.id.bt_PillInsert);
        bt_1time = (Button) findViewById(R.id.bt_1time);
        bt_2time = (Button) findViewById(R.id.bt_2time);
        bt_3time = (Button) findViewById(R.id.bt_3time);
        tv_1h = (TextView) findViewById(R.id.tv_1_h);
        tv_1m = (TextView) findViewById(R.id.tv_1_m);
        tv_2h = (TextView) findViewById(R.id.tv_2_h);
        tv_2m = (TextView) findViewById(R.id.tv_2_m);
        tv_3h = (TextView) findViewById(R.id.tv_3_h);
        tv_3m = (TextView) findViewById(R.id.tv_3_m);
        et_dosagi = (EditText) findViewById(R.id.et_Dosagi);
        et_DiseaseName = (EditText) findViewById(R.id.et_DiseaseName);

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);


        bt_List_Add.setEnabled(false);


        //인텐트로 넘어온값을 이용해 약 성분 자동 입력
        for(int i = 1; i < 4; i++){
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            lo_tab = (LinearLayout) inflater.inflate(R.layout.edittext, null);
            bt_add = (Button) lo_tab.findViewById(R.id.bt_Search_Add);
            et_Pill = (EditText) lo_tab.findViewById(R.id.et_Search);
            bt_delete = (Button) lo_tab.findViewById(R.id.bt_delete);
            inLayout.addView(lo_tab);
            pillList.add(Integer.parseInt(intent.getExtras().getString("num"+i)));
            et_Pill.setText(intent.getExtras().getString("pill"+i));
            //약 자동으로 추가되고 더이상 추가 할 수 없도록 버튼 disable
            et_Pill.setEnabled(false);
            bt_add.setEnabled(false);
        }

        //약 복용횟수 입력
        et_dosagi.setText(intent.getExtras().getString("dosagi"));

        // 버튼을 누르면 새 뷰가 추가됨.
        bt_List_Add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                lo_tab = (LinearLayout) inflater.inflate(R.layout.edittext, null);
                bt_add = (Button) lo_tab.findViewById(R.id.bt_Search_Add);
                et_Pill = (EditText) lo_tab.findViewById(R.id.et_Search);
                bt_delete = (Button) lo_tab.findViewById(R.id.bt_delete);
                inLayout.addView(lo_tab);

                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /* ((ViewManager)inLayout.getParent()).removeView(inLayout);*/
                    }
                });
                bt_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String test = et_Pill.getText().toString();
                        System.out.println(test);
                        Intent intent = new Intent(getApplicationContext(), SearchPill.class); // 다음 넘어갈 클래스 지정
                        // startActivity(intent);
                        startActivityForResult(intent, 3000);
                    }
                });
            }
        });

        //약 복용 시간 입력 다이얼로그
        dialog1 = new TimePickerDialog(this, listener1, 00, 00, true);
        bt_1time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.show();
            }
        });
        dialog2 = new TimePickerDialog(this, listener2, 00, 00, true);
        bt_2time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.show();
            }
        });
        dialog3 = new TimePickerDialog(this, listener3, 00, 00, true);
        bt_3time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.show();
            }
        });


        //약 등록버튼 누르면 서버로 요청
        bt_PillInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertPillList vo = new InsertPillList();
                vo.setDosagi(et_dosagi.getText().toString());
                vo.setElementList(pillList);
                vo.setName(et_DiseaseName.getText().toString());
                vo.setUserId(LoginActivity.userVO.getId());
                vo.setTimeList(time);
                System.out.println(et_dosagi.getText().toString() + pillList + et_DiseaseName.getText().toString() + time);
                Call<StatusVO> call = userService.postMyInserttPill(vo);
                call.enqueue(new Callback<StatusVO>() {
                    @Override
                    public void onResponse(Call<StatusVO> call, Response<StatusVO> response) {
                        StatusVO statusVO = response.body();
                        System.out.println("############" + statusVO.getStatus());
                        if (statusVO.getStatus().equals("201")) {
                            Toast.makeText(getApplicationContext(), "등록 성공", Toast.LENGTH_LONG).show();
                            finish();
                        } else if (statusVO.getStatus().equals("403"))
                            Toast.makeText(getApplicationContext(), "약 중복", Toast.LENGTH_LONG).show();
                        else if (statusVO.getStatus().equals("500"))
                            Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<StatusVO> call, Throwable t) {

                    }
                });

            }
        });

        Intent resultIntent = new Intent();
        setResult(5000,resultIntent);

    }

    private TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            h = String.valueOf(hourOfDay);
            m = String.valueOf(minute);
            tv_1h.setText(h + "시 ");
            tv_1m.setText(m + "분");
            time.add(h+m);
        }
    };
    private TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            h = String.valueOf(hourOfDay);
            m = String.valueOf(minute);
            tv_2h.setText(h + "시 ");
            tv_2m.setText(m + "분");
            time.add(h+m);
        }
    };
    private TimePickerDialog.OnTimeSetListener listener3 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {// 설정버튼 눌렀을 때
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            h = String.valueOf(hourOfDay);
            m = String.valueOf(minute);
            tv_3h.setText(h + "시 ");
            tv_3m.setText(m + "분");
            time.add(h+m);
        }
    };

    //이전화면으로 인텐트를 이용하여 정보를 같이 받아옴
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            int number = Integer.parseInt(data.getStringExtra("number"));
            pillList.add(number);
            et_Pill.setText(data.getStringExtra("result"));
            et_Pill.setEnabled(false);
            bt_add.setEnabled(false);
        }
    }

}
