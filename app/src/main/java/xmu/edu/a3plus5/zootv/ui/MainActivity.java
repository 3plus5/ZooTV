package xmu.edu.a3plus5.zootv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.ui.fragment.CategoryFragment;
import xmu.edu.a3plus5.zootv.ui.fragment.PieceFragment;
import xmu.edu.a3plus5.zootv.ui.fragment.RoomListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationBar.OnTabSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private long exitTime = 0;
    int lastSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_action_explore, "首页").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_action_explore, "分类").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_action_explore, "记录").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_action_explore, "个人").setActiveColor(R.color.orange))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);

//        getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment, new AdPagerFragment()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.category_fragment, new CategoryViewPagerFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new PieceFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast toast = Toast.makeText(this, "再按一次退出程序呦~", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout linearLayout = (LinearLayout) toast.getView();
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                messageTextView.setPadding(10, 0, 0, 0);
                messageTextView.setTextSize(18);
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
                imageView.setImageResource(R.drawable.push_chat_default);
                linearLayout.addView(imageView, 0);
                toast.show();
                exitTime = System.currentTimeMillis();
                return;
            }
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("搜房间，主播");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this,RoomListActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "提交了" + query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_button) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.zoo) {

        } else if (id == R.id.douyu) {

        } else if (id == R.id.huya) {

        } else if (id == R.id.xiongmao) {

        } else if (id == R.id.chushou) {

        } else if (id == R.id.huomao) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                ft.replace(R.id.main_content,new PieceFragment());
                break;
            case 1:
                ft.replace(R.id.main_content,new CategoryFragment());
                break;
            case 2:
                ft.replace(R.id.main_content,RoomListFragment.getRoomListFragment("炉石传说"));
                break;
            case 3:
                break;
        }
        ft.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
