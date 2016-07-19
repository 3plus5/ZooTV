package xmu.edu.a3plus5.zootv.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import xmu.edu.a3plus5.zootv.R;

public class FeedBackActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        initView();
        initData();
    }

    public void initView()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("意见反馈");
        setSupportActionBar(toolbar);
    }

    public void initData()
    {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
