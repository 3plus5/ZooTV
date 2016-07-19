package xmu.edu.a3plus5.zootv.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import de.hdodenhof.circleimageview.CircleImageView;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.dao.UserDao;
import xmu.edu.a3plus5.zootv.entity.Propensity;
import xmu.edu.a3plus5.zootv.entity.User;
import xmu.edu.a3plus5.zootv.ui.FeedBackActivity;
import xmu.edu.a3plus5.zootv.ui.LoginActivity;
import xmu.edu.a3plus5.zootv.ui.MainActivity;
import xmu.edu.a3plus5.zootv.ui.MyApplication;
import xmu.edu.a3plus5.zootv.ui.PropensityActivity;
import xmu.edu.a3plus5.zootv.widget.PieDialog;

/**
 * Created by asus1 on 2016/7/12.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.userPhoto)
    CircleImageView userPhoto;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.profile_sign_progressbar)
    ProgressBar signProgressBar;
    @Bind(R.id.profile_sign_btn)
    ImageView signButton;

    private View view;

    private UserDao userDao;

    private int signCount;
    private String nowDate;
    private String lastSignDate;
    private boolean isLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        userName.setText(MyApplication.user.getUserName());
        Picasso.with(getActivity()).load(MyApplication.user.getUserPic()).into(userPhoto);

        userDao = DaoFactory.getUserDao(getActivity());

        //JudgeSignStatus();

        return view;
    }

    @OnClick(R.id.userPhoto)
    public void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivityForResult(intent, 1);
    }

    @OnClick(R.id.profile_sign_btn)
    public void dailySignTest()
    {
        signCount = signProgressBar.getProgress();
        if (signCount < 7) {
            signCount += 1;
            signProgressBar.setProgress(signCount);
        }
        if(signCount == 7) {
            //抽奖
            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_present, null);
            final Dialog dialog = new Dialog(getActivity(), R.style.Translucent_NoTitle);
            dialog.setContentView(dialogView);
            dialog.show();
            ImageView presentImage = (ImageView) dialogView.findViewById(R.id.present_image);
            presentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            signCount = 0;
            signProgressBar.setProgress(signCount);
        }
    }

    public void dailySign() {
        try {
            if (daysBetween(lastSignDate, nowDate) == 1) {
                if (signCount < 7) {
                    signCount += 1;
                    signProgressBar.setProgress(signCount);
                }
                if(signCount == 7) {
                    //抽奖
                    View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_present, null);
                    final Dialog dialog = new Dialog(getActivity(), R.style.Translucent_NoTitle);
                    dialog.setContentView(dialogView);
                    dialog.show();
                    ImageView presentImage = (ImageView) dialogView.findViewById(R.id.present_image);
                    presentImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    signCount = 0;
                    signProgressBar.setProgress(signCount);
                }
            } else if(daysBetween(lastSignDate, nowDate) > 1) {
                signCount = 1;
                signProgressBar.setProgress(signCount);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        userDao.updateSignCount(MyApplication.user.getUserId(), signCount);
        userDao.updateSignDate(MyApplication.user.getUserId(), nowDate);

        signButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_done_black));
        signButton.setClickable(false);
    }

    @OnClick(R.id.profile_propensity_setting)
    public void setPropensity() {
        if (MyApplication.user.getUserName().equals("点击头像登录")) {
            Toast.makeText(getActivity(), "您还没有登录哦~", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getActivity(), PropensityActivity.class);
            getActivity().startActivityForResult(intent, 1);
        }
    }

    @OnClick(R.id.profile_propensity_statistic)
    public void showInterestDialog() {
        if ("点击头像登录".equals(MyApplication.user.getUserName())) {
            Toast.makeText(getActivity(), "您尚未登录哦", Toast.LENGTH_SHORT).show();
        } else {
            PieDialog pieDialog = new PieDialog();
            pieDialog.show(getFragmentManager(), "PieDialog");
        }
    }

    @OnClick(R.id.profile_cache_clear)
    public void clearCache()
    {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                        sDialog.setTitleText("Cancelled!")
                                .setContentText("Your imaginary file is safe :)")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Log.d("cgf", "wipe cache");
                        userDao.wipecache();

                        sDialog.setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    @OnClick(R.id.profile_about_us)
    public void aboutUs()
    {

    }

    @OnClick(R.id.profile_suggestion)
    public void provideSuggestion()
    {
        Intent intent = new Intent(getActivity(), FeedBackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.profile_version)
    public void detectVersion()
    {
        Toast.makeText(getActivity(), "版本检测中，请稍后", Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
    }

    /**
     * 计算两个日期之间相差天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 判断签到状态
     */
    public void JudgeSignStatus() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (MyApplication.user.getUserName().equals("点击头像登录")) {
            isLogin = false;
        } else {
            isLogin = true;
        }

        if (isLogin) {
            signCount = userDao.searchSignCount(MyApplication.user.getUserId());
            signProgressBar.setProgress(signCount);

            nowDate = sDateFormat.format(new java.util.Date());
            //nowDate = "2016-07-16";
            lastSignDate = userDao.searchLastSignDate(MyApplication.user.getUserId());
            try {
                if (daysBetween(lastSignDate, nowDate) == 0) {
                    signButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_done_black));
                    signButton.setClickable(false);
                } else if (daysBetween(lastSignDate, nowDate) == 1) {
                    signButton.setClickable(true);
                } else if (daysBetween(lastSignDate, nowDate) > 1) {
                    signButton.setClickable(true);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            signButton.setClickable(false);
        }
    }


}
