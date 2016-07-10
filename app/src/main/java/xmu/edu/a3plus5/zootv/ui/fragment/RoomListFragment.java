package xmu.edu.a3plus5.zootv.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.RoomListAdapter;
import xmu.edu.a3plus5.zootv.widget.MyGridView;

/**
 * Created by asus1 on 2016/7/10.
 */
public class RoomListFragment extends Fragment {

    @Bind(R.id.gridview)
    MyGridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview, container, false);
        ButterKnife.bind(this, view);
        RoomListAdapter adapter = new RoomListAdapter(this.getActivity());
        adapter.setMode(Attributes.Mode.Multiple);
        gridView.setAdapter(adapter);
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
                Toast.makeText(getActivity(),"跳转至房间",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

}
