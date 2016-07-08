package xmu.edu.a3plus5.zootv.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import xmu.edu.a3plus5.zootv.R;

/**
 * Created by hd_chen on 2016/7/8.
 */
public class CategoryGridAdapter extends BaseAdapter {

    int count;
    Context context;

    public CategoryGridAdapter (Context context,int count){
        this.context = context;
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(null == view){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.category_item,null);
            viewHolder.circleImageView = (CircleImageView)view.findViewById(R.id.item_image);
            viewHolder.textView = (TextView)view.findViewById(R.id.item_text);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        if(i == count-1){
            viewHolder.circleImageView.setImageResource(R.drawable.push_chat_default);
            viewHolder.textView.setText("更多");
        }else {
            viewHolder.circleImageView.setImageResource(R.drawable.push_chat_default);
            viewHolder.textView.setText("游戏类别");
        }
        return view;
    }
    class ViewHolder{
        CircleImageView circleImageView;
        TextView textView;
    }
}
