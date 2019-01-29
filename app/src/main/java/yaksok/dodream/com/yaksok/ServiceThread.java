/*
package yaksok.dodream.com.yaksok;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class ServiceThread extends Thread{

    Handler handler;
    boolean isRun = true;
    int pillTime;

    Intent intent = getIntent();

    public ServiceThread(Handler handler, int pillTime){
        this.pillTime = pillTime;
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        pillTime = Integer.parseInt(intent.getExtras().getString("time"));
        while(isRun){
            Log.d("AlarmThread","실행중");//쓰레드에 있는 핸들러에게 메세지를 보냄
            try{
                Log.d("Thread",String.valueOf(pillTime));
                Thread.sleep(pillTime*1000);
                handler.sendEmptyMessage(0);//10초씩 쉰다.
            }catch (Exception e) {}
        }
    }

    public Intent getIntent() {
        return intent;
    }
}*/
