package yaksok.dodream.com.yaksok;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.js.StatusVO;
import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.vo.TakeOk;

public class TakeMedicineDialog extends AppCompatActivity {
    Retrofit retrofit;
    UserService userService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

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
                if (mVO.getStatus().equals("201")) {
                    Log.d("약복용 서비스 ", "복용 완료");
                  //  startActivity(new Intent(this, LoginActivity.class));
                } else if (mVO.getStatus().equals("202")) {
                    Log.d("약복용 서비스 ", "복용 완료 후 약 삭제");
                }
            }

            @Override
            public void onFailure(Call<StatusVO> call, Throwable t) {
                System.out.println(t.fillInStackTrace().getMessage());
            }

        });

        finish();
        startActivity(new Intent(this, LoginActivity.class));


    }
}
