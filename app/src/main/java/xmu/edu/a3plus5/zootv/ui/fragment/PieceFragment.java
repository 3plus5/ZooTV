package xmu.edu.a3plus5.zootv.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.PieceAdapter;
import xmu.edu.a3plus5.zootv.entity.PieceHeader;
import xmu.edu.a3plus5.zootv.ui.MainActivity;

/**
 * Created by hd_chen on 2016/7/10.
 */
public class PieceFragment extends Fragment {

    @Bind(R.id.piece_recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piece_content, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        return view;
    }

    public void setUpRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        List<PieceHeader> pieceHeaders = new ArrayList<>();
        pieceHeaders.add(new PieceHeader("热门", "12", "www.douyu.com"));
        pieceHeaders.add(new PieceHeader("推荐", "12", "www.douyu.com"));
        pieceHeaders.add(new PieceHeader("英雄联盟", "12", "www.douyu.com"));
        PieceAdapter pieceAdapter = new PieceAdapter((MainActivity) getActivity(), pieceHeaders);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(pieceAdapter);
    }
}
