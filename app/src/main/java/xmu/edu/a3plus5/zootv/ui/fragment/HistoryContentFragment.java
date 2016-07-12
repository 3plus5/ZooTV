package xmu.edu.a3plus5.zootv.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.daimajia.swipe.util.Attributes;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.RoomListAdapter;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.widget.MyGridView;

/**
 * Created by asus1 on 2016/7/12.
 */
public class HistoryContentFragment extends Fragment {
    @Bind(R.id.gridview)
    MyGridView gridView;
    @Bind(R.id.pull_to_refresh)
    PullToRefreshView pullToRefreshView;

    private Category category;
    private boolean isRefreshing = false;

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static HistoryContentFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        HistoryContentFragment historyFragment = new HistoryContentFragment();
        historyFragment.setArguments(args);
        return historyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        if(getArguments() != null) {
            category = (Category) getArguments().getSerializable("category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview, container, false);
        ButterKnife.bind(this, view);
        gridView.setSelected(false);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "跳转至房间", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(true);
                        isRefreshing = true;
                        new RoomsAsyncTask().execute();
                    }
                }, 0);
            }
        });

        new RoomsAsyncTask().execute();

        return view;
    }

    class RoomsAsyncTask extends AsyncTask<Void, Void, Void> {

        RoomListAdapter adapter;
        List<Room> rooms;
        LinearLayoutManager manager;
        ProgressDialog progressDialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.setMode(Attributes.Mode.Multiple);
            gridView.setAdapter(adapter);
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
//            List<Category> categories = douYuPlatform.getPopularCategory();
            if(category == null){
                rooms = douYuPlatform.getByCategory(douYuPlatform.getPopularCategory().get(0), 1);
            }else {
                rooms = douYuPlatform.getByCategory(category, 1);
            }
            adapter = new RoomListAdapter(getActivity(),rooms);
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
