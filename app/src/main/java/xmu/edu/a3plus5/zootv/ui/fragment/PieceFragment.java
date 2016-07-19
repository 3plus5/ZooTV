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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.MainMultiAdapter;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.PieceHeader;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.ui.MyApplication;

/**
 * Created by hd_chen on 2016/7/10.
 */
public class PieceFragment extends Fragment {
    @Bind(R.id.piece_recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.pull_to_refresh)
    PullToRefreshView pullToRefreshView;
    ProgressDialog progressDialog;

    private View view;

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
        if (savedInstanceState != null) {
            return view;
        }

        view = inflater.inflate(R.layout.piece_content, container, false);

        ButterKnife.bind(this, view);

        Log.d("pieceFragment", "in create method");

        Gson gson = new Gson();
        Bundle bundle = getArguments();

        if(bundle != null){
            String labelsString = bundle.getString("labelsString");
            String piecesString = bundle.getString("piecesString");
            String categoriesString = bundle.getString("categoriesString");
            List<String> labels = gson.fromJson(labelsString, new TypeToken<List<String>>(){}.getType());
            Map<String, List> pieces = gson.fromJson(piecesString, new TypeToken<HashMap<String, List<Room>>>(){}.getType());
            List<Category> categories = gson.fromJson(categoriesString, new TypeToken<List<Category>>(){}.getType());

            Log.d("splashLabels", labelsString);
            Log.d("splashPieces", piecesString);
            Log.d("splashCategories", categoriesString);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new MainMultiAdapter(getActivity(), labels, pieces, categories));
        } else{
            setUpRecyclerView();
        }

        //setUpRecyclerView();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //progressDialog.dismiss();
        progressDialog = null;
    }

    class RoomsAsyncTask extends AsyncTask<Void, Void, Void> {

        MainMultiAdapter mainMultiAdapter;
        LinearLayoutManager manager;


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(mainMultiAdapter);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            pullToRefreshView.setRefreshing(false);
            isRefreshing = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            manager = new LinearLayoutManager(getActivity());
            BasePlatform platform = PlatformFactory.createPlatform(MyApplication.platform);
            List<Category> categories = platform.getPopularCategory();

            List<Category> selectCates = new ArrayList<>();
            List<String> labels = new ArrayList<>();
            labels.add("热门");
            labels.add("推荐");
            List<String> selectLabels = DaoFactory.getUserDao(getActivity()).selelabels(MyApplication.user.getUserId());
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
            Map<String, List> pieces = new HashMap<>();
            pieces.put("热门", platform.getMostPopularByPage(1));
            pieces.put("推荐", platform.getRecommendedRoomByCateList(selectCates));
            List<String> selelabels = DaoFactory.getUserDao(getActivity()).selelabels(MyApplication.user.getUserId());
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
            mainMultiAdapter = new MainMultiAdapter(getActivity(), labels, pieces, categories);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isRefreshing) {
                progressDialog = ProgressDialog.show(getActivity(), "", "数据载入中...", false);
                progressDialog.setCancelable(true);
            }
        }
    }
}
