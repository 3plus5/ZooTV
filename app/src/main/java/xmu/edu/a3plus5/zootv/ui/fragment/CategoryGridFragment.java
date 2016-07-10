package xmu.edu.a3plus5.zootv.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.CategoryGridAdapter;
import xmu.edu.a3plus5.zootv.widget.MyGridView;

/**
 * Created by hd_chen on 2016/7/8.
 */
public class CategoryGridFragment extends Fragment{

    @Bind(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @Bind(R.id.gridView)
    MyGridView gridView;

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
        setUpData();
        return view;
    }

    public void setUpData(){
        CategoryGridAdapter adapter = new CategoryGridAdapter(getActivity(),7);
        gridView.setNumColumns(7);
        gridView.setAdapter(adapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(dm.widthPixels / NUM);
        gridView.setStretchMode(GridView.NO_STRETCH);
        int count = adapter.getCount();
        gridView.setNumColumns(count);
    }

    private void getScreenDen() {
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }
}
