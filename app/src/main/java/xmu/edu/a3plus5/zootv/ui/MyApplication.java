package xmu.edu.a3plus5.zootv.ui;


import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/7/3.
 */
public class MyApplication extends Application {
    public static Context applicationContext;
    private static MyApplication instance;

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private static boolean activityVisible;
    private static String id = "";

    public static String getId() {
        return id;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed(String chatId) {
        activityVisible = true;
        id = chatId;
    }

    public static void activityPaused() {
        activityVisible = false;
        id = "";
    }

}