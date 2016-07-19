package xmu.edu.a3plus5.zootv.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import xmu.edu.a3plus5.zootv.R;

public class FeedBackActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.feedBackText)
    EditText feedBackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        toolbar.setTitle("意见反馈");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @OnClick(R.id.commit)
    public void commit() {
//        ShortMessage.ShareParams sp = new ShortMessage.ShareParams();
        Email.ShareParams sp = new Email.ShareParams();
        sp.setText(feedBackText.getText().toString());
        sp.setTitle("ZooTV意见反馈");
        sp.setAddress("xiaochen1995226@163.co");
        sp.setShareType(Platform.SHARE_APPS);
        Platform email = ShareSDK.getPlatform(Email.NAME);
        email.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {

            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
            }
        });
        email.share(sp);
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

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
