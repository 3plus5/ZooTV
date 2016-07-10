package xmu.edu.a3plus5.zootv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.entity.PieceHeader;
import xmu.edu.a3plus5.zootv.ui.MainActivity;
import xmu.edu.a3plus5.zootv.ui.fragment.RoomListFragment;
import xmu.edu.a3plus5.zootv.widget.MyGridView;

/**
 * Created by hd_chen on 2016/7/10.
 */
public class PieceAdapter extends RecyclerView.Adapter<PieceAdapter.MyViewHolder> {

    List<PieceHeader> pieceHeaders;
    Context context;

    public PieceAdapter(Context context, List<PieceHeader> pieceHeaders) {
        this.context = context;
        this.pieceHeaders = pieceHeaders;
    }

    @Override
    public PieceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.piece_item, parent, false);

        return new MyViewHolder(itemView, new MyViewHolder.MyViewHolderClicks() {
            @Override
            public void onItem(int position) {
                Toast.makeText(context, "点击了 " + pieceHeaders.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBindViewHolder(PieceAdapter.MyViewHolder holder, int position) {
        PieceHeader item = pieceHeaders.get(position);
        holder.title.setText(item.getTitle());
        RoomListAdapter adapter = new RoomListAdapter(context);
        adapter.setMode(Attributes.Mode.Multiple);
        holder.myGridView.setAdapter(adapter);
        holder.myGridView.setSelected(false);
        holder.myGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        holder.myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        holder.myGridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemSelected", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pieceHeaders.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MyViewHolderClicks mListener;
        @Bind(R.id.piece_title)
        TextView title;
        @Bind(R.id.piece_icon)
        ImageView icon;
        @Bind(R.id.piece_head)
        RelativeLayout header;
        @Bind(R.id.item_gridView)
        MyGridView myGridView;

        public MyViewHolder(View itemView, MyViewHolderClicks listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            header.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItem(getAdapterPosition());
        }

        public interface MyViewHolderClicks {
            void onItem(int position);
        }
    }
}
