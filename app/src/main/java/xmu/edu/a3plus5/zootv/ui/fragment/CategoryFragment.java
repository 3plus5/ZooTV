package xmu.edu.a3plus5.zootv.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xmu.edu.a3plus5.zootv.R;

/**
 * Created by hd_chen on 2016/7/11.
 */
public class CategoryFragment extends Fragment{

    private View view;

    private static CategoryFragment categoryFragment;

    public static synchronized CategoryFragment getCategoryFragment() {
        if (categoryFragment == null) {
            categoryFragment = new CategoryFragment();
        }
        return categoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null)
        {
            return view;
        }

        view = inflater.inflate(R.layout.category,null);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.category_frequent,new CategoryViewPagerFragment()).commit();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.category_all,new CategoryGridFragment()).commit();
        return view;
    }
}
