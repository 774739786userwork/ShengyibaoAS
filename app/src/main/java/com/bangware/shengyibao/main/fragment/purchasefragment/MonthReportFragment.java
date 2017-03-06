package com.bangware.shengyibao.main.fragment.purchasefragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangware.shengyibao.activity.R;
import com.github.mikephil.charting.charts.PieChart;

/**
 * 月报
 * Created by bangware on 2017/3/1.
 */

public class MonthReportFragment extends Fragment {
    private PieChart pieChart;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (view == null){
            view = inflater.inflate(R.layout.month_report_fragment, container,
                    false);
        }
        if(view!=null)
        {
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
