package xmu.edu.a3plus5.zootv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.entity.Category;
import xmu.edu.a3plus5.zootv.ui.fragment.CategoryGridFragment;
import xmu.edu.a3plus5.zootv.ui.fragment.LoginFragment;
import xmu.edu.a3plus5.zootv.ui.fragment.RoomListFragment;

public class LoginActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        ButterKnife.bind(this);
        toolbar.setTitle("ZooTV 登录");
        getSupportFragmentManager().beginTransaction().replace(R.id.category_list,new LoginFragment()).commit();
//        setSupportActionBar(toolbar);
        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
