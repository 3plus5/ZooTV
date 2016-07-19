package xmu.edu.a3plus5.zootv.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.MainMultiAdapter;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;

public class SplashActivity extends AppCompatActivity {

    private static final int SHOW_TIME_MIN = 1500; //最小跳转时间

    private List<Category> categories;
    private List<String> labels;
    private Map<String, List> pieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new MyAsyncTask().execute();
    }

    /**
     * 预加载网络数据
     * @return
     */
    private int loadCache()
    {
        BasePlatform platform = PlatformFactory.createPlatform(MyApplication.platform);
        categories = platform.getPopularCategory();

        List<Category> selectCates = new ArrayList<>();
        labels = new ArrayList<>();
        labels.add("热门");
        labels.add("推荐");
        List<String> selectLabels = DaoFactory.getUserDao(this).selelabels(MyApplication.user.getUserId());
        labels.addAll(selectLabels);
        if (selectLabels.size() != 0) {
            for (String label : selectLabels) {
                Category category = platform.getCategoryByName(label);
                if (category != null) {
                    selectCates.add(category);
                } else {
                    labels.remove(label);
                }
            }
        } else {
            //没有选择标签，则随机从最热标签中选3个
            List<Category> popularCategories = platform.getPopularCategory();
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                selectCates.add(popularCategories.get(random.nextInt(popularCategories.size())));
            }
        }
        pieces = new HashMap<>();
        pieces.put("热门", platform.getMostPopularByPage(1));
        pieces.put("推荐", platform.getRecommendedRoomByCateList(selectCates));
        List<String> selelabels = DaoFactory.getUserDao(SplashActivity.this).selelabels(MyApplication.user.getUserId());
        for (String label : selelabels) {
            Category category = platform.getCategoryByName(label);
            if (category != null) {
                List<Room> rooms1 = platform.getByCategory(category, 1);
                if (rooms1.size() > 6) {
                    pieces.put(label, rooms1.subList(0, 6));
                } else {
                    if(rooms1.size() == 0){
                        labels.remove(label);
                    }else {
                        pieces.put(label, rooms1);
                    }
                }
            }
        }
        //mainMultiAdapter = new MainMultiAdapter(this, labels, pieces, categories);

        return 1;
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPostExecute(Integer integer) {
            Log.d("splashActivity", "in post method");

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String labelsString = gson.toJson(labels, new TypeToken<List<String>>(){}.getType());
            Log.d("splashLabels", labelsString);
            String piecesString = gson.toJson(pieces, new TypeToken<HashMap<String, List<Room>>>(){}.getType());
            Log.d("splashPieces", piecesString);
            String categoriesString = gson.toJson(categories, new TypeToken<List<Category>>(){}.getType());
            Log.d("splashCategories", categoriesString);


            bundle.putString("labelsString", labelsString);
            bundle.putString("piecesString", piecesString);
            bundle.putString("categoriesString", categoriesString);
            intent.putExtras(bundle);
            Log.d("test", "position");
            startActivity(intent);
            finish();
            //两个参数分别表示进入的动画,退出的动画
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int result;
            long startTime = System.currentTimeMillis();
            result = loadCache();
            //result = 1;
            long loadingTime = System.currentTimeMillis() - startTime;
            Log.d("loadingTime", loadingTime+"");
            if(loadingTime < SHOW_TIME_MIN){
                try {
                    Thread.sleep(SHOW_TIME_MIN - loadingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
}
