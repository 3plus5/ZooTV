package xmu.edu.a3plus5.zootv.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.CategoryGridAdapter;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.dao.HistoryDao;
import xmu.edu.a3plus5.zootv.dao.UserDao;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.ui.MyApplication;
import xmu.edu.a3plus5.zootv.ui.RoomListActivity;
import xmu.edu.a3plus5.zootv.widget.MyGridView;

/**
 * Created by hd_chen on 2016/7/8.
 */
public class CategoryViewPagerFragment extends Fragment {

    @Bind(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @Bind(R.id.gridView)
    MyGridView gridView;

    String type;


    public static CategoryViewPagerFragment getCategoryViewPagerFragment(String type) {
        CategoryViewPagerFragment categoryViewPagerFragment = new CategoryViewPagerFragment();
        Bundle args = new Bundle();
        args.putString("Type", type);
        categoryViewPagerFragment.setArguments(args);
        return categoryViewPagerFragment;
    }

    DisplayMetrics dm;
    private int NUM = 4; // 每行显示个数

    private int LIEWIDTH;//每列宽度

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("Type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_viewpager, container, false);
        ButterKnife.bind(this, view);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        getScreenDen();
        LIEWIDTH = dm.widthPixels / NUM;
        new MyAsyncTask().execute();
        return view;
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        CategoryGridAdapter categoryGridAdapter;
        List<Category> categories;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            gridView.setNumColumns(7);
            gridView.setAdapter(categoryGridAdapter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(categoryGridAdapter.getCount() * LIEWIDTH,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            gridView.setLayoutParams(params);
            gridView.setColumnWidth(dm.widthPixels / NUM);
            gridView.setStretchMode(GridView.NO_STRETCH);
            int count = categoryGridAdapter.getCount();
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), RoomListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("category", categories.get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            gridView.setNumColumns(count);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if ("home".equals(type)) {
                categories = new ArrayList<>();
                BasePlatform platform = PlatformFactory.createPlatform(MyApplication.platform);
                categories = platform.getPopularCategory().subList(0, 8);
                categoryGridAdapter = new CategoryGridAdapter(getActivity(), categories, "category");
            } else {
                categories = new ArrayList<>();
                List<Room> historyRooms;
                BasePlatform platform;
                HistoryDao historyDao = DaoFactory.getHistoryDao(getActivity());
                if (!"点击头像登录".equals(MyApplication.user.getUserName())) {
                    historyRooms = historyDao.selehistoryRoom(MyApplication.user.getUserId());
                    for (Room room : historyRooms) {
                        platform = PlatformFactory.createPlatform(room.getPlatform());
                        BasePlatform platform1 = PlatformFactory.createPlatform(MyApplication.platform);
                        room = platform.getRoomById(room.getRoomId());
                        Category category = platform1.getCategoryByName(room.getCate());
                        if(category != null) {
                            Iterator iterator = categories.iterator();
                            while (iterator.hasNext()) {
                                Category cate = (Category) iterator.next();
                                if (cate.getName().equals(category.getName())) {
                                    iterator.remove();
                                }
                            }
//                        BasePlatform mplatform = PlatformFactory.createPlatform(MyApplication.platform);
//                        if (mplatform.getCategoryByName(category.getName()) != null) {
                            categories.add(category);
//                        }
                        }
                    }
                    Collections.reverse(categories);
                }
                if (categories.size() >= 8) {
                    categories = categories.subList(0, 7);
                } else {
                    BasePlatform platform1 = PlatformFactory.createPlatform(MyApplication.platform);
                    List<Category> moreCates = platform1.getAllCategory().subList(0, 8);
//                    Iterator iterator = moreCates.iterator();
//                    while (iterator.hasNext()){
//                        Category cate = (Category) iterator.next();
//                        for(Category category : categories){
//                            if(category.getName().equals(cate.getName())){
//                                iterator.remove();
//                            }
//                        }
//                    }
                    categories.addAll(moreCates);
                }
            }
            categoryGridAdapter = new CategoryGridAdapter(getActivity(), categories, "category");
            return null;
        }
    }

//    public void setUpData() {
//        CategoryGridAdapter adapter = new CategoryGridAdapter(getActivity(), 7, "category");
//        gridView.setNumColumns(7);
//        gridView.setAdapter(adapter);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        gridView.setLayoutParams(params);
//        gridView.setColumnWidth(dm.widthPixels / NUM);
//        gridView.setStretchMode(GridView.NO_STRETCH);
//        int count = adapter.getCount();
//        gridView.setNumColumns(count);
//    }

    private void getScreenDen() {
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }
}
