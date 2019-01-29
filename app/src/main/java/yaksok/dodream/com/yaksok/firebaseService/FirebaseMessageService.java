package yaksok.dodream.com.yaksok.firebaseService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import yaksok.dodream.com.yaksok.ChatRoom;
import yaksok.dodream.com.yaksok.ChattingMenu;
import yaksok.dodream.com.yaksok.ChattingRoom;
import yaksok.dodream.com.yaksok.LoginActivity;
import yaksok.dodream.com.yaksok.MainPageActivity;
import yaksok.dodream.com.yaksok.MessageServiced;
import yaksok.dodream.com.yaksok.MessageStatuse;
import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.vo.message.SendMessageVO;

import static com.google.firebase.messaging.RemoteMessage.*;
import static yaksok.dodream.com.yaksok.ChattingRoom.iInTheChattingRoom;

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String NOTIFICATION_CHANNEL_ID = "7788";
    String decodeName, decodeMessage, decodeId;
    String channelId;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    public MessageServiced delegate;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("message??", "메시지 왔어요");

        // 이거 추가 하면
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE );
        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG" );
        wakeLock.acquire(3000);




        try {
            decodeName = URLDecoder.decode(remoteMessage.getData().get("name"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            decodeMessage = URLDecoder.decode(remoteMessage.getData().get("message"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            decodeId = URLDecoder.decode(remoteMessage.getData().get("id"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        sendNotification(decodeName, decodeMessage);
    }

    private void sendNotification(String title, String message) { //FCM서버에서 메세지를 앱으로 보내줄시 백그라운드에서 받는 알림내용
        Intent intent = new Intent(this, ChattingRoom.class);
        intent.putExtra("send_user",decodeName);
        intent.putExtra("recived_message",decodeMessage);
        intent.putExtra("goBack","123");
        intent.putExtra("user_id", LoginActivity.userVO.getId());
        intent.putExtra("your_id",decodeId);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        if(!ChattingRoom.iInTheChattingRoom){
            channelId = getString(R.string.default_notification_channel_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title) //알림제목
                    .setContentText(message)    //알림내용
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent);
                Log.d("dddddd","실행됨");


            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "channel",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());



        }


        else if(ChattingRoom.iInTheChattingRoom){

            Log.d("Recive_Msg","good" + LoginActivity.userVO.getId());

           /* SendMessageVO sendMessageVO = new SendMessageVO();
            sendMessageVO.setGivingUser(decodeId);
            sendMessageVO.setContent(decodeMessage);
            sendMessageVO.setReceivingUser(LoginActivity.userVO.getId());
            sendMessageVO.setRegidate("19:15");
            ChattingRoom.albumList.add(sendMessageVO);

            //ChattingRoom.linearLayoutManager.setStackFromEnd(true);
            ChattingRoom.mRecyclerView.setAdapter(ChattingRoom.myRecyclerAdapter);
            ChattingRoom.mRecyclerView.setLayoutManager(ChattingRoom.linearLayoutManager);*/

           ChatRoom.setGivingUser(decodeId);
           ChatRoom.setContent(decodeMessage);
           ChatRoom.setRecevingUser(LoginActivity.userVO.getId());
           ChatRoom.setRegidate("19:15");

           //delegate.MessageProcess(MessageStatuse.MessageSuccess);


            /*linearLayoutManager.setStackFromEnd(true);
            mRecyclerView.setAdapter(myRecyclerAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);*/
           /* PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            try {
                // Perform the operation associated with our pendingIntent
                pendingIntent2.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }*/



        }





    }
}