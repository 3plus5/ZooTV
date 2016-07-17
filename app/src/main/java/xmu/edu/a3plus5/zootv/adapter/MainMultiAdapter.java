package xmu.edu.a3plus5.zootv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.PieceHeader;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.ui.MyApplication;
import xmu.edu.a3plus5.zootv.ui.RoomListActivity;
import xmu.edu.a3plus5.zootv.widget.MyGridView;

/**
 * Created by hd_chen on 2016/7/10.
 */
public class MainMultiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> labels;
    Map<String,List> pieces;
    Context context;
    List<Category> categories;

    DisplayMetrics dm;
    private int NUM = 4; // 每行显示个数

    private int LIEWIDTH;//每列宽度

    public static enum ITEM_TYPE {
        HEADER_TYPE,
        CONTENT_TYPE
    }

    public MainMultiAdapter(Context context, List<String> labels, Map<String,List> pieces, List<Category> categories) {
        this.context = context;
        this.labels = labels;
        this.pieces = pieces;
        this.categories = categories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == ITEM_TYPE.CONTENT_TYPE.ordinal()) {
            itemView = LayoutInflater.from(context).inflate(R.layout.piece_item, parent, false);
            return new ContentViewHolder(itemView, new ContentViewHolder.MyViewHolderClicks() {
                @Override
                public void onItem(final int position) {
                    if (position != 1 && position != 2) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(context, RoomListActivity.class);
                                BasePlatform platform = PlatformFactory.createPlatform(MyApplication.platform);
                                intent.putExtra("category", platform.getCategoryByName(labels.get(position - 1)));
                                context.startActivity(intent);
                            }
                        }).start();

                    }
                }
            }, pieces);
        } else {
            itemView = LayoutInflater.from(context).inflate(R.layout.main_header, parent, false);
            return new HeaderViewHolder(itemView, context, categories);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            String item = labels.get(position - 1);
            ((ContentViewHolder) holder).title.setText(item);
            RoomListAdapter adapter = new RoomListAdapter(context, pieces.get(item));
            adapter.setMode(Attributes.Mode.Multiple);
            ((ContentViewHolder) holder).myGridView.setAdapter(adapter);
            ((ContentViewHolder) holder).myGridView.setSelected(false);
            ((ContentViewHolder) holder).myGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return false;
                }
            });
            ((ContentViewHolder) holder).myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });

            ((ContentViewHolder) holder).myGridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Log.d("onItemSelected", "onItemSelected:" + position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(context, "点击了" + ((HeaderViewHolder) holder).categories.get(i).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            ((HeaderViewHolder) holder).relativeLayout.setVisibility(View.GONE);
            ((HeaderViewHolder) holder).horizontalScrollView.setHorizontalScrollBarEnabled(false);

            dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            LIEWIDTH = dm.widthPixels / NUM;

            CategoryGridAdapter adapter = new CategoryGridAdapter(context, categories, "home");
            ((HeaderViewHolder) holder).gridView.setNumColumns(categories.size() + 1);
            ((HeaderViewHolder) holder).gridView.setAdapter(adapter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            ((HeaderViewHolder) holder).gridView.setLayoutParams(params);
            ((HeaderViewHolder) holder).gridView.setColumnWidth(dm.widthPixels / NUM);
            ((HeaderViewHolder) holder).gridView.setStretchMode(MyGridView.NO_STRETCH);
        }
    }

    @Override
    public int getItemCount() {
        return labels.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE.HEADER_TYPE.ordinal() : ITEM_TYPE.CONTENT_TYPE.ordinal();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MyViewHolderClicks mListener;
        @Bind(R.id.piece_title)
        TextView title;
        @Bind(R.id.piece_icon)
        ImageView icon;
        @Bind(R.id.piece_head)
        RelativeLayout header;
        @Bind(R.id.item_gridView)
        MyGridView myGridView;

        Map<String,List> pieces;

        public ContentViewHolder(View itemView, MyViewHolderClicks listener, Map<String,List> pieces) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            this.pieces = pieces;
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

    static class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ViewPager.OnPageChangeListener {

        @Bind(R.id.viewPager)
        ViewPager viewPager;
        @Bind(R.id.viewGroup)
        ViewGroup viewGroup;
        @Bind(R.id.horizontalScrollView)
        HorizontalScrollView horizontalScrollView;
        @Bind(R.id.gridView)
        MyGridView gridView;
        @Bind(R.id.category_head)
        RelativeLayout relativeLayout;

//        DisplayMetrics dm;
//        private int NUM = 4; // 每行显示个数
//
//        private int LIEWIDTH;//每列宽度

        Context context;
        List<Category> categories;

        //装点点的ImageView数组
        private ImageView[] tips;
        //装ImageView数组
        private ImageView[] mImageViews;
        //图片资源id
        private int[] imgIdArray;
        public ImageHandler handler = new ImageHandler(new WeakReference<Context>(context));

        public HeaderViewHolder(View itemView, Context context, List<Category> categories) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this, itemView);
            setUpViewPager();
            this.categories = categories;
//            CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(context, categories, "home");
//            gridView.setNumColumns(categories.size() + 1);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(categoryGridAdapter.getCount() * LIEWIDTH,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            gridView.setLayoutParams(params);
//            gridView.setColumnWidth(dm.widthPixels / NUM);
//            gridView.setStretchMode(GridView.NO_STRETCH);
//            gridView.setAdapter(categoryGridAdapter);
//            horizontalScrollView.setHorizontalScrollBarEnabled(false);
//            LIEWIDTH = dm.widthPixels / NUM;
//            CategoryGridAdapter adapter = new CategoryGridAdapter(context, categories, "home");
//            gridView.setNumColumns(categories.size() + 1);
//            gridView.setAdapter(adapter);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            gridView.setLayoutParams(params);
//            gridView.setColumnWidth(dm.widthPixels / NUM);
//            gridView.setStretchMode(MyGridView.NO_STRETCH);
//            int count = adapter.getCount();
//            gridView.setNumColumns(count);

//            setUpData();
        }

        @Override
        public void onClick(View view) {

        }

//        public void setUpData() {
//            CategoryGridAdapter adapter = new CategoryGridAdapter(context, categories, "home");
//            gridView.setNumColumns(categories.size() + 1);
//            gridView.setAdapter(adapter);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * LIEWIDTH,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            gridView.setLayoutParams(params);
//            gridView.setColumnWidth(dm.widthPixels / NUM);
//            gridView.setStretchMode(MyGridView.NO_STRETCH);
//            int count = adapter.getCount();
//            gridView.setNumColumns(count);
//        }


        public void setUpViewPager() {
            //载入图片资源ID
            imgIdArray = new int[]{R.drawable.ad3, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5};

            //将点点加入到ViewGroup中
            tips = new ImageView[imgIdArray.length];
            for (int i = 0; i < tips.length; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(3, 3));
                tips[i] = imageView;
                if (i == 0) {
                    Picasso.with(context).load(R.drawable.ic_action_star).into(tips[i]);
//                    tips[i].setBackgroundResource(R.drawable.ic_action_star);
                } else {
                    Picasso.with(context).load(R.drawable.ic_stars).into(tips[i]);
//                    tips[i].setBackgroundResource(R.drawable.ic_stars);
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutParams.leftMargin = 5;
                layoutParams.rightMargin = 5;
                viewGroup.addView(imageView, layoutParams);
            }

            //将图片装载到数组中
            mImageViews = new ImageView[imgIdArray.length];
            for (int i = 0; i < mImageViews.length; i++) {
                if (mImageViews[i] == null) {
                    ImageView imageView = new ImageView(context);
                    imageView.setBackgroundResource(imgIdArray[i]);
                    mImageViews[i] = imageView;
                }
            }
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                //配合Adapter的currentItem字段进行设置。
                @Override
                public void onPageSelected(int arg0) {
                    handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                //覆写该方法实现轮播效果的暂停和恢复
                @Override
                public void onPageScrollStateChanged(int arg0) {
                    switch (arg0) {
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                            break;
                        case ViewPager.SCROLL_STATE_IDLE:
                            handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                            break;
                        default:
                            break;
                    }
                }
            });

            //设置Adapter
            viewPager.setAdapter(new MyAdapter());
            //设置监听，主要是设置点点的背景
            viewPager.setOnPageChangeListener(this);
            //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
            viewPager.setCurrentItem((mImageViews.length) * 100);
            handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            setImageBackground(state % mImageViews.length);
        }

        public class MyAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
//            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

            }

            /**
             * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
             */
            @Override
            public Object instantiateItem(View container, int position) {
                try {
                    ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
                } catch (Exception e) {

                }
                return mImageViews[position % mImageViews.length];
            }
        }

        /**
         * 设置选中的tip的背景
         *
         * @param selectItems
         */
        private void setImageBackground(int selectItems) {
            for (int i = 0; i < tips.length; i++) {
                if (i == selectItems) {
                    tips[i].setBackgroundResource(R.drawable.ic_action_star);
                } else {
                    tips[i].setBackgroundResource(R.drawable.ic_stars);
                }
            }
        }

        class ImageHandler extends Handler {

            /**
             * 请求更新显示的View。
             */
            protected static final int MSG_UPDATE_IMAGE = 1;
            /**
             * 请求暂停轮播。
             */
            protected static final int MSG_KEEP_SILENT = 2;
            /**
             * 请求恢复轮播。
             */
            protected static final int MSG_BREAK_SILENT = 3;
            /**
             * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
             * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
             * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
             */
            protected static final int MSG_PAGE_CHANGED = 4;

            //轮播间隔时间
            protected static final long MSG_DELAY = 3000;

            //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
            private WeakReference<Context> weakReference;
            private int currentItem = 0;

            protected ImageHandler(WeakReference<Context> wk) {
                weakReference = wk;
            }

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Context activity = weakReference.get();
                if (activity == null) {
                    //Activity已经回收，无需再处理UI了
                    return;
                }
                //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
                if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
                    handler.removeMessages(MSG_UPDATE_IMAGE);
                }
                switch (msg.what) {
                    case MSG_UPDATE_IMAGE:
                        currentItem++;
                        viewPager.setCurrentItem(currentItem);
                        //准备下次播放
                        handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                        break;
                    case MSG_KEEP_SILENT:
                        //只要不发送消息就暂停了
                        break;
                    case MSG_BREAK_SILENT:
                        handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                        break;
                    case MSG_PAGE_CHANGED:
                        //记录当前的页号，避免播放的时候页面显示不正确。
                        currentItem = msg.arg1;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
