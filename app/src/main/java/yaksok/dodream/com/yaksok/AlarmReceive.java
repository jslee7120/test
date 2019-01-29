package yaksok.dodream.com.yaksok;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceive extends BroadcastReceiver {   //BroadcastReceiver 가필요함

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    final String TAG = "BOOT_START_SERVICE";
    public static String userId, pillNo;


    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        Log.d("알람여부", "리시브 들어옴");
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
      //  Notification notify = new Notification(android.R.drawable.ic_input_add, "text", System.currentTimeMillis());

        Intent intentActivity = new Intent(context, MainPageActivity.class); //그메세지를 클릭했을때 불러올엑티비티를 설정함
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);//플레그부분은 옵션인데 나도 자세하게 몰르겠음
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentActivity, 0);
        String ticker = "ticker";//여긴 알림바에 등록될 글이랑 타이틀 적는곳.
        String title = "알림";
        String text = "약 복용시간 입니다";
// Create Notification Object
   /*   Notification  Notification notification = new Notification
                (android.R.drawable.ic_input_add, ticker, System.currentTimeMillis());//알림바에 넣을 이미지 아이콘

        notification.setLatestEventInfo(context,   title, text, pendingIntent);
        nm.notify(1, notification);//노티피에 1주는건 왜지??? 그것도 모르겠음.

    }*/
        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_input_add)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setTicker(ticker);
        Notification notification = builder.build();
        nm.notify(1, notification);

        userId = intent.getStringExtra("userId");
        pillNo = intent.getStringExtra("pillNo");

        Log.d("제발",intent.getStringExtra("userId"));
        Intent intent_ = new Intent(context, Alarm_On.class);
        intent_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);// 이거 안해주면 안됨
        context.startActivity(intent_);


    }
}
