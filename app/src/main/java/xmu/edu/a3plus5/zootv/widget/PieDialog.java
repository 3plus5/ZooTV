package xmu.edu.a3plus5.zootv.widget;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xmu.edu.a3plus5.zootv.R;
import xmu.edu.a3plus5.zootv.dao.DaoFactory;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.network.BasePlatform;
import xmu.edu.a3plus5.zootv.network.PlatformFactory;
import xmu.edu.a3plus5.zootv.ui.MyApplication;

/**
 * Created by hd_chen on 2016/7/15.
 */
public class PieDialog extends DialogFragment {
    @Bind(R.id.pieChart)
    PieChart mChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_dialog, container);
        ButterKnife.bind(this, view);
        new MyAsynctask().execute();
        return view;
    }

    class MyAsynctask extends AsyncTask<Void, Void, Void> {

        private PieData mPieData;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showChart(mChart, mPieData);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mPieData = getPieData();
            return null;
        }
    }

    private void showChart(PieChart pieChart, PieData pieData) {

        pieChart.setHoleRadius(55f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("");
        pieChart.setDescriptionTextSize(30);
        pieChart.setDescriptionPosition(680f, 450f);

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("您的偏好");  //饼状图中间的文字
        pieChart.setCenterTextSize(20);

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

//        Legend mLegend = pieChart.getLegend();  //设置比例图
//        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
////      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
//        mLegend.setXEntrySpace(7f);
//        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    private PieData getPieData() {
        List<Room> rooms = DaoFactory.getHistoryDao(getActivity()).selehistoryRoom(MyApplication.user.getUserId());
        ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容
        ArrayList<Entry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (rooms.size() == 0) {
            xValues.add("您尚未有观看记录哦");
            yValues.add(new Entry(1, 0));
            colors.add(Color.GRAY);
        } else {
            for (Room room : rooms) {
                BasePlatform platform = PlatformFactory.createPlatform(room.getPlatform());
                room = platform.getRoomById(room.getRoomId());
                int index = xValues.indexOf(room.getCate());
                if (xValues.contains(room.getCate())) {
                    yValues.set(index, new Entry(yValues.get(index).getVal() + 1, index));
                } else {
                    xValues.add(room.getCate());
                    yValues.add(new Entry(1, index));
                }
            }
            colors.add(Color.YELLOW);
            colors.add(Color.CYAN);
            colors.add(Color.GREEN);
            colors.add(Color.LTGRAY);
            colors.add(Color.DKGRAY);
            colors.add(Color.MAGENTA);
            colors.add(Color.BLUE);
        }

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

//        int plus = 0;
//        int range = 170/xValues.size();
//        for(int i = 0 ; i < xValues.size() ; i++){
//            colors.add(Color.rgb(60+plus,40+plus,90+plus));
//            plus += range;
//        }

        pieDataSet.setColors(colors);
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        Log.d("values", xValues.toString());
        Log.d("values", pieDataSet.toString());
        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}
