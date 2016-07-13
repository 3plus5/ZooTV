package xmu.edu.a3plus5.zootv.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.dao.UserDao;
import xmu.edu.a3plus5.zootv.entity.History;
import xmu.edu.a3plus5.zootv.entity.Interest;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.ui.WebActivity;

public class RoomListAdapter extends BaseSwipeAdapter {

    private Context mContext;
    List<Room> rooms;
    private UserDao userdao;


    public RoomListAdapter(Context mContext, List<Room> rooms) {
        this.rooms = rooms;
        this.mContext = mContext;

        userdao = DaoFactory.getUserDao(mContext);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    /**
     * 获取每个item的view
     *
     * @param position
     * @param parent
     * @return
     */
    @Override
    public View generateView(final int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);

        ImageView attention = (ImageView) view.findViewById(R.id.attention);
        ImageView share = (ImageView) view.findViewById(R.id.share);
        ImageView room = (ImageView) view.findViewById(R.id.room_photo);
        TextView title = (TextView) view.findViewById(R.id.room_title);
        TextView author = (TextView) view.findViewById(R.id.room_author);
        TextView audience = (TextView) view.findViewById(R.id.room_audience);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.room_up);

        ViewHolder viewHolder = new ViewHolder(attention, share, room, title, author, audience, linearLayout);
        view.setTag(viewHolder);

        return view;
    }

    /**
     * 为每个item进行赋值和事件监听
     *
     * @param position
     * @param convertView
     */
    @Override
    public void fillValues(final int position, View convertView) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title.setText(rooms.get(position).getTitle());
        viewHolder.author.setText(rooms.get(position).getAnchor());
        viewHolder.audience.setText(rooms.get(position).getWatchingNum());
        Picasso.with(mContext).load(rooms.get(position).getPicUrl()).into(viewHolder.room);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Room room = rooms.get(position);
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", room.getLink());
                mContext.startActivity(intent);
                //根据roomId 和 platform 唯一确认一个room，若数据库中有room，则不加入
                //然后在history中加入room，如果之前有，则删除再插到第一条
                userdao.addhistory(1, 1);
                Toast.makeText(mContext, "历史记录~~", Toast.LENGTH_SHORT).show();
                List<History> histories = userdao.selehistory(1);
                for (int i = 0; i < histories.size(); i++) {
                    Toast.makeText(mContext, histories.get(i) + " ** ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userdao.addinterest(1, 1);
                //据roomId 和 platform 唯一确认一个room，若数据库中有room，则不加入
                //点击查询是否被关注，并弹出相应提示
                //查询关注表，之前有关注则删除，没关注则添加
                Toast.makeText(mContext, "关注成功~~", Toast.LENGTH_SHORT).show();
                List<Interest> interests = userdao.seleinterest(1);
                for (int i = 0; i < interests.size(); i++) {
                    Toast.makeText(mContext, interests.get(i) + " ** ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "share", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 用一个类保存item中的元素
     */
    public static class ViewHolder {
        public ImageView attention;
        public ImageView share;
        public ImageView room;
        public TextView title;
        public TextView author;
        public TextView audience;
        public LinearLayout linearLayout;

        public ViewHolder(ImageView attention, ImageView share, ImageView room, TextView title, TextView author, TextView audience, LinearLayout linearLayout) {
            this.attention = attention;
            this.share = share;
            this.room = room;
            this.title = title;
            this.author = author;
            this.audience = audience;
            this.linearLayout = linearLayout;
        }
    }
}
