package xmu.edu.a3plus5.zootv.ui;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.entity.User;
import xmu.edu.a3plus5.zootv.network.BasePlatform;

/**
 * Created by Administrator on 2015/7/3.
 */
public class MyApplication extends Application {
    public static Context applicationContext;
    private static MyApplication instance;

    public static User user;

    private static final String TAG = "MyApplication";

    public static String platform = BasePlatform.Zoo;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        user = new User();
        user.setUserName("点击头像登录");
        user.setUserPhoto(R.drawable.push_chat_default+"");
        initImageLoader();
    }

    public static void setPlatform(String mplatform){
        platform = mplatform;
    }

    void initImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(applicationContext)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
//            .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
//            .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(applicationContext, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);
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