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
import android.util.MutableBoolean;

import java.util.List;

import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.ui.MainActivity;
import xmu.edu.a3plus5.zootv.ui.MyApplication;

public class NotificationService extends Service {

    int count = 0;
    private List<Room> rooms;
    private boolean isLogin = false;

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
                for (int i = 0; true; ) {
                    sleep(5000);
                    if ("点击头像登录".equals(MyApplication.user.getUserName())) {
                        isLogin = false;
                    }
                    if (!isLogin)
                        rooms = getInterest();
                    if (rooms != null) {
                        isLogin = true;
                        for (Room r : rooms) {
                            int beforeStatus = r.getStatus();
                            Room cur = PlatformFactory.createPlatform(r.getPlatform()).getRoomById(r.getRoomId());
                            if(cur != null){
                                r.setStatus(cur.getStatus());
                                if (beforeStatus == 0 && r.getStatus() == 1) {
                                    r.setAnchor(cur.getAnchor());
                                    r.setTitle(cur.getTitle());
                                    count++;
                                    showNotification(r);
                                }
                            }
                        }
                        i++;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Room> getInterest() {
        if ("点击头像登录".equals(MyApplication.user.getUserName())) {
            return null;
        } else {
            List<Room> rooms = DaoFactory.getInterestDao(getApplicationContext()).seleinterestRoom(MyApplication.user.getUserId());
            return rooms;
        }
    }

    //弹出Notification
    private void showNotification(Room room) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        PendingIntent pendingIntent3 = PendingIntent.getActivity(this, 0,
//                new Intent(this, MainActivity.class), 0);
        Notification notify = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setTicker("您关注的主播" + room.getAnchor() + "开播了哦~")
                .setContentTitle("ZooTV")
                .setContentText("您关注的主播" + room.getAnchor() + "开播了哦~")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[]{0, 200, 200, 200})
                .setLights(Color.GREEN, 1000, 1000)
                .setNumber(1)
                .build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(count, notify);
    }
}