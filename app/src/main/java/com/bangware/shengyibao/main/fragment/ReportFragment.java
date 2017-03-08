package com.bangware.shengyibao.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.main.fragment.fundmanager.FundReportActivity;
import com.bangware.shengyibao.main.fragment.purchasefragment.PurchaseReportActivity;
import com.bangware.shengyibao.main.fragment.salerreportmanager.SalerReportActivity;
import com.bangware.shengyibao.main.fragment.storemanager.StoreReportActivity;

/**
 * Created by bangware on 2017/2/22.
 */

public class ReportFragment extends Fragment {
    private View view = null;
    private RelativeLayout purchaseReportLayuot,salerReportLayuot,storeReportLayuot,fundReportLayuot;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        setListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.activity_chart_lib, container,false);
        }
        if(view!=null)
        {
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void findViews(){
        purchaseReportLayuot = (RelativeLayout) getView().findViewById(R.id.purchase_report_rel);
        salerReportLayuot = (RelativeLayout) getView().findViewById(R.id.saler_report_rel);
        storeReportLayuot = (RelativeLayout) getView().findViewById(R.id.store_report_rel);
        fundReportLayuot = (RelativeLayout) getView().findViewById(R.id.fund_report_rel);
    }

    private void  setListener(){
        MyOnclickListener listener = new MyOnclickListener();
        purchaseReportLayuot.setOnClickListener(listener);
        salerReportLayuot.setOnClickListener(listener);
        storeReportLayuot.setOnClickListener(listener);
        fundReportLayuot.setOnClickListener(listener);
    }

    private class MyOnclickListener implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.purchase_report_rel:
                    intent = new Intent(getActivity(), PurchaseReportActivity.class);
                    startActivity(intent);
                    break;
                case R.id.saler_report_rel:
                    intent = new Intent(getActivity(), SalerReportActivity.class);
                    startActivity(intent);
                    break;
                case R.id.store_report_rel:
                    intent = new Intent(getActivity(), StoreReportActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fund_report_rel:
                    intent = new Intent(getActivity(), FundReportActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
