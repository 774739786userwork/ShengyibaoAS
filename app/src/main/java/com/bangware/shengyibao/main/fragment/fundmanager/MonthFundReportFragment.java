package com.bangware.shengyibao.main.fragment.fundmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.main.fragment.fundmanager.entity.FunkBean;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

/**
 * Created by bangware on 2017/3/8.
 */

public class MonthFundReportFragment extends Fragment{
    private PieChart mPieChart;
    private ListView lv;
    private ArrayList<FunkBean> list = null;
    private FundReportAdapter adapter = null;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        PieData pieData = getPieData(4,100);
        initPieData(mPieChart,pieData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (view == null){
            view = inflater.inflate(R.layout.month_funk_report_fragment,container,false);
        }
        if (view != null){
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init(){
        mPieChart = (PieChart) getView().findViewById(R.id.monthfunkpieChart);
        lv = (ListView) getView().findViewById(R.id.monthfundListview);

        list = getData();
        adapter = new FundReportAdapter(getContext(),list);
        lv.setAdapter(adapter);
    }

    private ArrayList<FunkBean> getData() {
        list = new ArrayList<FunkBean>();
        for (int i = 0; i < 10; i++){
            FunkBean funkBean = new FunkBean();
            funkBean.setImg(R.drawable.btn_logout_normal);
            funkBean.setPayName("银行支付");
            funkBean.setPayMoney("1888.00万");
            list.add(funkBean);
        }
        return list;
    }

    /**饼图数据*/
    private void initPieData(PieChart pieChart, PieData pieData){
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

//        pieChart.setDescription("测试饼状图");

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
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("资金账户");  //饼状图中间的文字
        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        pieChart.animateXY(1000, 1000);  //设置动画
    }

    /**
     *
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        xValues.add("银行支付");
        xValues.add("银行支付");
        xValues.add("银行支付");
        xValues.add("银行支付");
        /*for (int i = 0; i < count; i++) {
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }*/

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = 15;
        float quarterly2 = 25;
        float quarterly3 = 30;
        float quarterly4 = 30;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues,"");
        pieDataSet.setSliceSpace(5f); //设置各饼状图之间的距离
        pieDataSet.setValueTextSize(10f);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}
