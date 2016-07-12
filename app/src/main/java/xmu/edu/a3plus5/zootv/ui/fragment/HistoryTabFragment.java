package xmu.edu.a3plus5.zootv.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.adapter.HistoryFragmentPagerAdapter;

/**
 * Created by asus1 on 2016/7/12.
 */
public class HistoryTabFragment extends Fragment {
    @Bind(R.id.history_viewpager)
    ViewPager viewPager;
    @Bind(R.id.history_sliding_tabs)
    TabLayout tabLayout;

    private FragmentManager fm;
    private HistoryFragmentPagerAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  fm = getFragmentManager();
        fm = getChildFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_tab, container, false);

        ButterKnife.bind(this, view);

        pagerAdapter = new HistoryFragmentPagerAdapter(fm, this.getActivity());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        return view;
    }
}
