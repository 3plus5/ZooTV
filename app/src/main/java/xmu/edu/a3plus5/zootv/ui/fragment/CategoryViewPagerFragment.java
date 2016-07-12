package xmu.edu.a3plus5.zootv.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.CategoryGridAdapter;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
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

    private static CategoryViewPagerFragment categoryViewPagerFragment;

    public static synchronized CategoryViewPagerFragment getCategoryViewPagerFragment() {
        if (categoryViewPagerFragment == null) {
            categoryViewPagerFragment = new CategoryViewPagerFragment();
        }
        return categoryViewPagerFragment;
    }

    DisplayMetrics dm;
    private int NUM = 4; // 每行显示个数

    private int LIEWIDTH;//每列宽度

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

    class MyAsyncTask extends AsyncTask<Void,Void,Void>{

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
                    bundle.putSerializable("category",categories.get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            gridView.setNumColumns(count);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BasePlatform douYuPlatform = PlatformFactory.createPlatform(BasePlatform.DouYu);
            categories = douYuPlatform.getPopularCategory();
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
