package xmu.edu.a3plus5.zootv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import xmu.edu.a3plus5.zootv.R;

/**
 * Created by asus1 on 2016/7/19.
 */
public class FeedBackAdapter extends BaseAdapter {

    private Context context;
    private String[] feedbacks;

    public FeedBackAdapter(Context context, String[] feedbacks)
    {
        this.context = context;
        this.feedbacks = feedbacks;
    }

    @Override
    public int getCount() {
        return feedbacks.length;
    }

    @Override
    public Object getItem(int i) {
        return feedbacks[i];
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.feedback_item, null);
        TextView tv = (TextView) view.findViewById(R.id.feedback_item);
        tv.setText(feedbacks[i]);
        tv.setTextSize(15);

        return view;
    }
}
