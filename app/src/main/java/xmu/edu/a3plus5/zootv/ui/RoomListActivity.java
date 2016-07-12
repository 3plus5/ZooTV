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
import xmu.edu.a3plus5.zootv.ui.fragment.RoomListFragment;

public class RoomListActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Category category = (Category) intent.getSerializableExtra("category");
        String query = (String) intent.getSerializableExtra("query");
        if(category!=null) {
            toolbar.setTitle(category.getName());
            getSupportFragmentManager().beginTransaction().replace(R.id.room_list,RoomListFragment.getRoomListFragment(category)).commit();
        }else if(query != null){
            toolbar.setTitle(query);
            getSupportFragmentManager().beginTransaction().replace(R.id.room_list,RoomListFragment.getRoomListFragment(query)).commit();
        }
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
