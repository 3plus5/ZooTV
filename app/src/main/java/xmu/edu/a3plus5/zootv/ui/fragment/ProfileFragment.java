package xmu.edu.a3plus5.zootv.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.ui.LoginActivity;

/**
 * Created by asus1 on 2016/7/12.
 */
public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.profile_login)
    public void login(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, 1);
    }
}
