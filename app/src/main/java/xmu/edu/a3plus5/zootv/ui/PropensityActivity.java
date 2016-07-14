package xmu.edu.a3plus5.zootv.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.FlowLayoutAdapter;
import xmu.edu.a3plus5.zootv.adapter.TextTagsAdapter;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.dao.UserDao;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.entity.Propensity;
import xmu.edu.a3plus5.zootv.entity.User;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.network.ZooPlatform;
import xmu.edu.a3plus5.zootv.widget.FlowLayout;

public class PropensityActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView tv_remind;

    private FlowLayout tcy_my_label;
    private TagCloudView tcy_hot_label;
    private FlowLayoutAdapter mMyLabelAdapter;
    private TextTagsAdapter mHotLabelAdapter;
    private List<String> MyLabelLists, HotLabelLists;

    private UserDao userDao;
    private User user = null;
    private BasePlatform zooPlatform;
    private List<Category> categories;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propensity);

        userDao = DaoFactory.getUserDao(PropensityActivity.this);
        user = MyApplication.user;

        new MyAsyncTask().execute();
        initView();
        initData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("标签选择");
        setSupportActionBar(toolbar);

        tv_remind = (TextView) findViewById(R.id.tv_remind);
        tcy_my_label = (FlowLayout) findViewById(R.id.tcy_my_label);
        tcy_hot_label = (TagCloudView) findViewById(R.id.tcy_hot_label);
    }

    private void initData() {
        /*String[] date = getResources().getStringArray(R.array.tags);
        HotLabelLists = new ArrayList<>();
        for (int i = 0; i < date.length; i++) {
            HotLabelLists.add(date[i]);
        }*/
        mHotLabelAdapter = new TextTagsAdapter(PropensityActivity.this, HotLabelLists);
        TextTagsAdapter.MyOnClickListener myOnClickListener = new TextTagsAdapter.MyOnClickListener() {
            @Override
            public void myOnClick(int position, String content) {
                if (MyLabelLists.size() < 5) {
                    Boolean isExits = isExist(MyLabelLists,
                            HotLabelLists.get(position));
                    if (isExits) {
                        Toast.makeText(PropensityActivity.this, "此标签已经添加啦", Toast.LENGTH_LONG).show();
                        return;
                    }
                    MyLabelLists.add(HotLabelLists.get(position));
                    ChangeMyLabels();
                } else {
                    Toast.makeText(PropensityActivity.this, "最多只能添加5个标签", Toast.LENGTH_LONG).show();
                }
            }
        };
        mHotLabelAdapter.setMyOnClickListener(myOnClickListener);
        tcy_hot_label.setAdapter(mHotLabelAdapter);

        MyLabelLists = new ArrayList<>();
        MyLabelLists = userDao.selelabels(user.getUserId());

        mMyLabelAdapter = new FlowLayoutAdapter(PropensityActivity.this, MyLabelLists);
        tcy_my_label.setAdapter(mMyLabelAdapter);
        tcy_my_label.setItemClickListener(new TagCloudLayoutItemOnClick(0));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cgf", "back");
                onBackPressed();
            }
        });

    }

    /**
     * 刷新我的标签数据
     */
    private void ChangeMyLabels() {
        tv_remind.setVisibility(MyLabelLists.size() > 0 ? View.GONE
                : View.VISIBLE);
        tcy_my_label.setVisibility(MyLabelLists.size() > 0 ? View.VISIBLE
                : View.GONE);
        mMyLabelAdapter.notifyDataSetChanged();
    }

    /**
     * 标签的点击事件
     *
     * @author lijuan
     */
    class TagCloudLayoutItemOnClick implements FlowLayout.TagItemClickListener {
        int index;

        public TagCloudLayoutItemOnClick(int index) {
            this.index = index;
        }

        @Override
        public void itemClick(int position) {
            switch (index) {
                case 0:
                    MyLabelLists.remove(MyLabelLists.get(position));
                    ChangeMyLabels();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 将数组里面的字符串遍历一遍，看是否存在相同标签
     *
     * @param str
     * @param compareStr
     * @return
     */
    public static Boolean isExist(List<String> str, String compareStr) {
        Boolean isExist = false;//默认沒有相同标签
        for (int i = 0; i < str.size(); i++) {
            if (compareStr.equals(str.get(i))) {
                isExist = true;
            }
        }
        return isExist;
    }

    /**
     * 添加toolbar图标
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.propensity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.propensity_done_button) {
            userDao.deletelabels(user.getUserId());
            userDao.addlabel(user.getUserId(), MyLabelLists);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PropensityActivity.this,"","数据载入中...",false);
            progressDialog.setCancelable(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            zooPlatform = PlatformFactory.createPlatform(BasePlatform.Zoo);
            categories = zooPlatform.getAllCategory();

            HotLabelLists = new ArrayList<>();
            int listSize = categories.size();
            if(listSize > 20){
                listSize = 20;
            }
            for(int i = 0; i < listSize; i++)
            {
                Log.d("cgf", categories.get(i).getName());
                HotLabelLists.add(categories.get(i).getName());
            }
            return null;
        }
    }
}
