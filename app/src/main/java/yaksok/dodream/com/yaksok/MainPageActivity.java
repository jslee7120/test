package yaksok.dodream.com.yaksok;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import yaksok.dodream.com.yaksok.js.NearTimeMedicineVO;
import yaksok.dodream.com.yaksok.js.StatusVO;
import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.vo.TakeMedicineVO;
import yaksok.dodream.com.yaksok.vo.TakeOk;

public class MainPageActivity extends AppCompatActivity {
    private String img_url;
    Button bt_chat, bt_InsertPill, btn_addFamily, btn_shopping;

    Retrofit retrofit;
    UserService userService;

    TextView tv_main_hour, tv_main_min;
    Date now;
    String curTime;
    public static int n;
    int times, m, h;
    int t1, ctime, ptime, pilltime_h, pilltime_m;
    AlarmManager am;
    int countTime_hour;
    int countTime_min;
    int pillTime_day = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        //Log.d("outOfChattingRoom","aa"+ChattingRoom.iInTheChattingRoom);


        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        tv_main_hour = (TextView) findViewById(R.id.tv_main_hour);
        tv_main_min = (TextView) findViewById(R.id.tv_main_min);

        now = new Date();
        SimpleDateFormat time = new SimpleDateFormat("HHmm");
        curTime = time.format(now);

        bt_chat = (Button) findViewById(R.id.bt_Chat);
        bt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChattingMenu.class));
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);

            }
        });


        bt_InsertPill = (Button) findViewById(R.id.bt_InsertPill);
        bt_InsertPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), InsertPillActivity.class),4000);
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);
            }
        });


        btn_addFamily = (Button) findViewById(R.id.bt_InsertFamily);
        btn_addFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddYourFmaily.class));
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);
            }
        });

        btn_shopping = (Button) findViewById(R.id.bt_Shopping);
        btn_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Shopings.class));
                overridePendingTransition(R.anim.pull_out_left, R.anim.pull_in_right);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
       /* if(n==1) {
            takeMedicine();
            n=0;
        }
*/
        pillTime();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.activity_customize_actionbar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(getApplicationContext(), "눌림", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainPageActivity.this, SettingPage.class);
                startActivity(intent);
                return true;
//                startActivity(new Intent(getApplicationContext(),SettingPage.class));
//                return  true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    class BackThread extends Thread {
        boolean s = true;
        int x = ++ctime;

        @Override
        public void run() {
            while (true) {
                // 메인에서 생성된 Handler 객체의 sendEmpryMessage 를 통해 Message 전달
                Log.d("x= ", String.valueOf(x));
                try {
                    Thread.sleep(1000);
                    Date nowTime = new Date();
                    Log.d("countDownTimer", "success");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
                    String countTime = timeFormat.format(nowTime);
                    int countTime_hour = Integer.parseInt(countTime.substring(0, 2));
                    int countTime_min = Integer.parseInt(countTime.substring(2));
                    Log.d("countTIme", countTime);
                    if (Integer.parseInt(countTime) < ptime) {//다음약이 오늘 일 때(초로 계산)
                        Log.d("지금", "성공");
                        times = ((pilltime_h * 3600) + (pilltime_m * 60)) - ((countTime_hour * 3600) + (countTime_min * 60));
                        if (times < 3600)
                            h = 0;
                        else
                            h = times / 3600;

                        m = (times % 3600 / 60);
                    } else {//다음약이 내일일때(초로 계산)
                        t1 = (((23 * 3600) + (59 * 60)) - ((countTime_hour * 3600) + (countTime_min * 60)));
                        times = (t1 + ((pilltime_h * 3600) + (pilltime_m * 60)));
                        m = (times % 3600 / 60);
                        h = times / 3600;
                    }

                    handler.sendEmptyMessage(0);

                    if (m / 10 == 0)
                        handler.sendEmptyMessage(1);
                    else
                        handler.sendEmptyMessage(2);//시간의 나머지초/60하여 분으로 표시
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // end while
        } // end run()
    } // end class BackThread

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {   // Message id 가 0 이면
                tv_main_hour.setText(h + "");// 메인스레드의 UI 내용 변경
            }
            if (msg.what == 1) {
                tv_main_min.setText("0" + String.valueOf(m).substring(0));
            }
            if (msg.what == 2) {
                tv_main_min.setText(String.valueOf(m).substring(0, 2));
            }
        }
    };

    public void pillTime() {
        if(LoginActivity.userVO.getId() == null){
            return;
        }
        else {
            Call<NearTimeMedicineVO> call = userService.getNearTime(LoginActivity.userVO.getId());
            call.enqueue(new Callback<NearTimeMedicineVO>() {
                @Override
                public void onResponse(Call<NearTimeMedicineVO> call, Response<NearTimeMedicineVO> response) {
                    NearTimeMedicineVO nearTimeMedicineVO = response.body();
                    System.out.println("############" + nearTimeMedicineVO.getStatus());
                    if (nearTimeMedicineVO.getStatus().equals("200")) {
                        Toast.makeText(getApplicationContext(), "알림이 있습니다", Toast.LENGTH_LONG).show();
                        TakeMedicineVO.setGivingUser(LoginActivity.userVO.getId());
                        TakeMedicineVO.setMedicineNO(String.valueOf(nearTimeMedicineVO.getResult().getMyMedicineNo()));

                        //현재 시간, 서버에서 받은시간의 시간과 분 나누는 곳
                        int nowtime_hour = Integer.parseInt(curTime.substring(0, 2));
                        int nowtime_min = Integer.parseInt(curTime.substring(2));
                        pilltime_h = Integer.parseInt(nearTimeMedicineVO.getResult().getTime().substring(0, 2));
                        pilltime_m = Integer.parseInt(nearTimeMedicineVO.getResult().getTime().substring(2));
                        ptime = Integer.parseInt(nearTimeMedicineVO.getResult().getTime().substring(0, 4));

                        if (Integer.parseInt(curTime) <= ptime) {//다음약이 오늘 일 때(초로 계산)
                            Log.d("if1", "오늘");
                            times = ((pilltime_h * 3600) + (pilltime_m * 60)) - ((nowtime_hour * 3600) + (nowtime_min * 60));
                            if (times < 3600)
                                h = 0;
                            else {
                                h = times / 3600;
                                m = (times % 3600 / 60) + 1;
                            }
                        } else {
                            pillTime_day = 1;//다음약이 내일일(초로 계산)
                            t1 = (((23 * 3600) + (59 * 60)) - ((nowtime_hour * 3600) + (nowtime_min * 60)));
                            times = (t1 + ((pilltime_h * 3600) + (pilltime_m * 60)));
                            h = times / 3600;
                            m = (times % 3600 / 60);
                        }


                        tv_main_hour.setText(h + "");
                        if (m / 10 == 0)
                            tv_main_min.setText("0" + String.valueOf(m).substring(0));
                        else
                            tv_main_min.setText(String.valueOf(m).substring(0, 2));//시간의 나머지초/60하여 분으로 표시
                        ctime = Integer.parseInt(curTime.substring(0, 4));
                        BackThread thread = new BackThread();
                        thread.setDaemon(true);
                        thread.start();
                        Toast.makeText(getApplicationContext(), "Service 시작", Toast.LENGTH_SHORT).show();

                   /* Intent intent = new Intent(MainPageActivity.this, MyService.class);
                    intent.putExtra("pillTime", String.valueOf(times));*/

                        Log.d("Test1:", String.valueOf(times));
                        alarm_on();

                    } else if (nearTimeMedicineVO.getStatus().equals("204"))
                        Toast.makeText(getApplicationContext(), "등록된 약이 없습니다", Toast.LENGTH_LONG).show();
                    else if (nearTimeMedicineVO.getStatus().equals("500"))
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<NearTimeMedicineVO> call, Throwable t) {

                }
            });

        }
    }

    public void alarm_on() {
        // 알람 등록하기
        Log.i("alarm", "setAlarm");


        Intent intent = new Intent(this, AlarmReceive.class);//AlarmReceive.class이클레스는 따로 만들꺼임 알람이 발동될때 동작하는 클레이스임

        Log.d("TakeMe", ""+TakeMedicineVO.getGivingUser());
        intent.putExtra("userId", TakeMedicineVO.getGivingUser());
        intent.putExtra("pillNo", TakeMedicineVO.getMedicineNO());

        PendingIntent pender = PendingIntent.getBroadcast(MainPageActivity.this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기

        Log.d("알림 시간", calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE) + "/" + pilltime_h + ":" + pilltime_m);
        if (pillTime_day == 1) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DATE), pilltime_h + 24, pilltime_m);
            Log.d("음..","내일약");
        }
        else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , calendar.get(Calendar.DATE), pilltime_h, pilltime_m);//시간을 셋팅
            Log.d("음..","오늘약");
        }
        //알람 예약
        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pender);//이건 한번 알람
        //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, sender);//이건 여러번 알람 24*60*60*1000 이건 하루에한번 계속 알람한다는 뜻.
        Toast.makeText(MainPageActivity.this, "시간설정:" + Integer.toString(calendar.get(calendar.HOUR_OF_DAY)) + ":" + Integer.toString(calendar.get(calendar.MINUTE)), Toast.LENGTH_LONG).show();
    }

   /* public void takeMedicine() {
        //커밋??
        TakeOk takeOk = new TakeOk();
        takeOk.setGivingUser(AlarmReceive.userId);
        takeOk.setMyMedicineNo(AlarmReceive.pillNo);
        // Log.d("Check",intent.getStringExtra("uId") + takeOk.getGivingUser());

        Log.d("스태틱 값", AlarmReceive.pillNo + AlarmReceive.userId);
        Log.d("takeOk값", takeOk.getGivingUser() + takeOk.getMyMedicineNo());

        Call<StatusVO> call = userService.putTakeMedicine(takeOk);
        System.out.println("@@@@@@@@@@@@@@@@@@@");
        call.enqueue(new Callback<StatusVO>() {
            @Override
            public void onResponse(Call<StatusVO> call, Response<StatusVO> response) {
                StatusVO mVO = response.body();
//                    System.out.println("@@@@@@@@@@@@@@@@@@@" + mVO.getStatus());
                if (mVO.getStatus().equals("200")) {
                    Log.d("약복용 서비스 ", "복용 완료");
                } else if (mVO.getStatus().equals("202")) {
                    Log.d("약복용 서비스 ", "복용 완료 후 약 삭제");
                }
            }

            @Override
            public void onFailure(Call<StatusVO> call, Throwable t) {
                System.out.println(t.fillInStackTrace().getMessage());
            }

        });


    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.d("requestCode",String.valueOf(requestCode));
            finish();
            startActivity(new Intent(this, MainPageActivity.class));

    }

}