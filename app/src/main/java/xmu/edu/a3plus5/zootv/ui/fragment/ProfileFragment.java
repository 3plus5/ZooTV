package xmu.edu.a3plus5.zootv.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import de.hdodenhof.circleimageview.CircleImageView;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.entity.Propensity;
import xmu.edu.a3plus5.zootv.ui.LoginActivity;
import xmu.edu.a3plus5.zootv.ui.MainActivity;
import xmu.edu.a3plus5.zootv.ui.MyApplication;
import xmu.edu.a3plus5.zootv.ui.PropensityActivity;

/**
 * Created by asus1 on 2016/7/12.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.userPhoto)
    CircleImageView userPhoto;
    @Bind(R.id.user_name)
    TextView userName;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null)
        {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        userName.setText(MyApplication.user.getUserName());
        Picasso.with(getActivity()).load(MyApplication.user.getUserPic()).into(userPhoto);
        return view;
    }

    @OnClick(R.id.userPhoto)
    public void login(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivityForResult(intent, 1);
    }

    @OnClick(R.id.profile_propensity_setting)
    public void setPropensity()
    {
        Intent intent = new Intent(getActivity(), PropensityActivity.class);
        getActivity().startActivityForResult(intent, 1);
    }
}
