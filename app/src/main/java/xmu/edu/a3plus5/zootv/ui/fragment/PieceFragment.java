package xmu.edu.a3plus5.zootv.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.CategoryGridAdapter;
import xmu.edu.a3plus5.zootv.adapter.MainMultiAdapter;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.PieceHeader;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;

/**
 * Created by hd_chen on 2016/7/10.
 */
public class PieceFragment extends Fragment {
    @Bind(R.id.piece_recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.pull_to_refresh)
    PullToRefreshView pullToRefreshView;

    private static PieceFragment pieceFragment;
    private boolean isRefreshing = false;

    public static synchronized PieceFragment getPieaceFragment() {
        if (pieceFragment == null) {
            pieceFragment = new PieceFragment();
        }
        return pieceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piece_content, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(true);
                        isRefreshing = true;
                        setUpRecyclerView();
                    }
                }, 0);
            }
        });
        return view;
    }

    public void setUpRecyclerView() {
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        List<PieceHeader> pieceHeaders = new ArrayList<>();
//        pieceHeaders.add(new PieceHeader("热门", "12", "www.douyu.com"));
//        pieceHeaders.add(new PieceHeader("推荐", "12", "www.douyu.com"));
//        pieceHeaders.add(new PieceHeader("英雄联盟", "12", "www.douyu.com"));
//        MainMultiAdapter mainMultiAdapter = new MainMultiAdapter(getActivity(), pieceHeaders);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(mainMultiAdapter);
        new RoomsAsyncTask().execute();
    }



    class RoomsAsyncTask extends AsyncTask<Void, Void, Void> {

        MainMultiAdapter mainMultiAdapter;
        LinearLayoutManager manager;
        ProgressDialog progressDialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(mainMultiAdapter);
            if(progressDialog != null) {
                progressDialog.dismiss();
            }
            pullToRefreshView.setRefreshing(false);
            isRefreshing = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            manager = new LinearLayoutManager(getActivity());
            BasePlatform douYuPlatform = PlatformFactory.createPlatform(BasePlatform.DouYu);
            List<Category> categories = douYuPlatform.getPopularCategory();
            List<Room> rooms = douYuPlatform.getByCateGory(categories.get(0), 1);
            List<PieceHeader> pieceHeaders = new ArrayList<>();
            pieceHeaders.add(new PieceHeader("热门", "12", "www.douyu.com"));
//            pieceHeaders.add(new PieceHeader("推荐", "12", "www.douyu.com"));
//            pieceHeaders.add(new PieceHeader("英雄联盟", "12", "www.douyu.com"));
            mainMultiAdapter = new MainMultiAdapter(getActivity(), pieceHeaders, rooms, categories);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!isRefreshing) {
                progressDialog = ProgressDialog.show(getActivity(), "", "数据载入中...", false);
            }
        }
    }
}
