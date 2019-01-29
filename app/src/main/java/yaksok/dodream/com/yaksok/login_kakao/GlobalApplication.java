package yaksok.dodream.com.yaksok.login_kakao;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application{

    private static volatile GlobalApplication globalApplication = null;
    private static volatile Activity currentActivity = null;

    public void onCreate(){
        super.onCreate();
        globalApplication = this;
        KakaoSDK.init(new KakaoSdkAdapter());
    }

    public static GlobalApplication getGlobalApplication() {
        return globalApplication;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    //Activity에 올라올떄마다 onCreate에서 호출해야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        globalApplication = null;
    }
}
