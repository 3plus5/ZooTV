package xmu.edu.a3plus5.zootv.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.ui.MainActivity;

public class NotificationService extends Service {

    int count = 0;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("NotificationService", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("NotificationService", "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        new PollingThread().start();
        Log.d("NotificationService", "onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("NotificationService", "onDestroy");
    }

    class PollingThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {sleep(60000);
                    count++;
                    showNotification();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //弹出Notification
    private void showNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        Notification notify = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setTicker("您关注的主播开播了哦~")
                .setContentTitle("ZooTV")
                .setContentText("您关注的主播开播了哦~")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[]{0, 200, 200, 200})
                .setLights(Color.GREEN, 1000, 1000)
                .setContentIntent(pendingIntent3).setNumber(1).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(count, notify);
    }
}