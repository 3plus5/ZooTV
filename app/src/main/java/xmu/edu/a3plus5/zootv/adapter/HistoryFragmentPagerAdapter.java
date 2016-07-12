package xmu.edu.a3plus5.zootv.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xmu.edu.a3plus5.zootv.ui.fragment.HistoryContentFragment;

/**
 * Created by asus1 on 2016/7/12.
 */
public class HistoryFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"我的关注","历史记录"};
    private Context context;
    private HistoryContentFragment fragment1, fragment2;

    public HistoryFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;


    }

    @Override
    public Fragment getItem(int position) {
     return HistoryContentFragment.newInstance(position+1);

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
