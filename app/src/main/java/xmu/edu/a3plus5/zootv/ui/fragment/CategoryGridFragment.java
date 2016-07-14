package xmu.edu.a3plus5.zootv.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.CategoryGridAdapter;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.ui.MyApplication;
import xmu.edu.a3plus5.zootv.widget.MyGridView;

/**
 * Created by hd_chen on 2016/7/8.
 */
public class CategoryGridFragment extends Fragment {

    @Bind(R.id.gridView)
    MyGridView gridView;
    ProgressDialog progressDialog;

    private static CategoryGridFragment categoryGridFragment;

    public static synchronized CategoryGridFragment getCategoryGridFragment() {
        if (categoryGridFragment == null) {
            categoryGridFragment = new CategoryGridFragment();
        }
        return categoryGridFragment;
    }

    DisplayMetrics dm;
    private int NUM = 3; // 每行显示个数

    private int LIEWIDTH;//每列宽度

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_all, container, false);
        ButterKnife.bind(this, view);
        getScreenDen();
        LIEWIDTH = dm.widthPixels / NUM;
//        setUpData();
        new MyAsyncTask().execute();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
        progressDialog = null;
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        CategoryGridAdapter adapter;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            gridView.setNumColumns(3);
            gridView.setAdapter(adapter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            gridView.setLayoutParams(params);
            gridView.setColumnWidth(dm.widthPixels / NUM);
            gridView.setStretchMode(GridView.NO_STRETCH);
            if (progressDialog != null)
                progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BasePlatform douYuPlatform = PlatformFactory.createPlatform(MyApplication.platform);
            List<Category> categories = douYuPlatform.getAllCategory();
            adapter = new CategoryGridAdapter(getActivity(), categories, "category");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "", "数据载入中...", false);
            progressDialog.setCancelable(true);
        }
    }

//    public void setUpData() {
//        CategoryGridAdapter adapter = new CategoryGridAdapter(getActivity(), 100, "category");
//        gridView.setNumColumns(3);
//        gridView.setAdapter(adapter);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        gridView.setLayoutParams(params);
//        gridView.setColumnWidth(dm.widthPixels / NUM);
//        gridView.setStretchMode(GridView.NO_STRETCH);
//    }

    private void getScreenDen() {
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }
}
