package xmu.edu.a3plus5.zootv.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.ui.WebActivity;

public class RoomListAdapter extends BaseSwipeAdapter {

    private Context mContext;
    List<Room> rooms;

    public RoomListAdapter(Context mContext, List<Room> rooms) {
        this.rooms = rooms;
        this.mContext = mContext;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    /**
     * 为每个item添加事件
     *
     * @param position
     * @param parent
     * @return
     */
    @Override
    public View generateView(final int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);

        ImageView attention = (ImageView) view.findViewById(R.id.attention);
        ImageView trash = (ImageView) view.findViewById(R.id.trash);
        ImageView room = (ImageView) view.findViewById(R.id.room_photo);
        TextView title = (TextView) view.findViewById(R.id.room_title);
        title.setText(rooms.get(position).getTitle());
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.room_up);
        Picasso.with(mContext).load(rooms.get(position).getPicUrl()).into(room);

//        room.setImageResource(R.drawable.room);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url",rooms.get(position).getLink());
                mContext.startActivity(intent);
            }
        });
        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "attention", Toast.LENGTH_SHORT).show();
            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "trash", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * 为每个item进行赋值
     *
     * @param position
     * @param convertView
     */
    @Override
    public void fillValues(int position, View convertView) {
        /*TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText((position + 1 )+".");*/
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
