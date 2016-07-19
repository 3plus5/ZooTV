package xmu.edu.a3plus5.zootv.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.ui.CategoryListActivity;
import xmu.edu.a3plus5.zootv.ui.MyApplication;
import xmu.edu.a3plus5.zootv.ui.RoomListActivity;

/**
 * Created by hd_chen on 2016/7/8.
 */
public class CategoryGridAdapter extends BaseAdapter {

    int count;
    Context context;
    String type;
    List<Category> categories;

    public CategoryGridAdapter(Context context, List<Category> categories, String type) {
        this.context = context;
        this.categories = categories;
        this.type = type;
        this.count = categories.size();
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
        final Category category = categories.get(i);
        if (null == view) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.category_item, null);
            viewHolder.circleImageView = (CircleImageView) view.findViewById(R.id.item_image);
            viewHolder.textView = (TextView) view.findViewById(R.id.item_text);
            viewHolder.item_layout = (LinearLayout) view.findViewById(R.id.category_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if ("home".equals(type) && i == count - 1) {
            Picasso.with(context).load(R.drawable.view_more).into(viewHolder.circleImageView);
            viewHolder.textView.setText("更多");
            viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CategoryListActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
//            ImageLoader imageLoader = ImageLoader.getInstance();
//            imageLoader.displayImage(categories.get(i).getPicUrl(), viewHolder.circleImageView);
            Picasso.with(context).load(categories.get(i).getPicUrl()).into(viewHolder.circleImageView);
            viewHolder.textView.setText(categories.get(i).getName());

            viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RoomListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("category", category);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }

    class ViewHolder {
        CircleImageView circleImageView;
        TextView textView;
        LinearLayout item_layout;
    }
}
