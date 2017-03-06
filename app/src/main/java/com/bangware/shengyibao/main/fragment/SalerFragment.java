package com.bangware.shengyibao.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.daysaleaccount.view.SaleAccountListActivity;
import com.bangware.shengyibao.deliverynote.view.DeliveryNoteMouthQueryActivity;
import com.bangware.shengyibao.salesamount.view.SaleRankingActivity;
import com.bangware.shengyibao.salesamount.view.SalesAmountActivity;

/**
 * 销售管理
 * Created by bangware on 2017/2/15.
 */

public class SalerFragment extends Fragment {
    private RelativeLayout rankingSaler,totalMoneySaler,recordSaler,accountSaler;
    private View view = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.acticity_saler_fragment, container,
                    false);
        }
        if(view!=null)
        {
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        setListener();
    }

    private void init(){
        rankingSaler = (RelativeLayout) getView().findViewById(R.id.saler_ranking_rel);
        totalMoneySaler = (RelativeLayout) getView().findViewById(R.id.saler_money_rel);
        recordSaler = (RelativeLayout) getView().findViewById(R.id.saler_record_rel);
        accountSaler = (RelativeLayout) getView().findViewById(R.id.saler_account_rel);
    }

    private void  setListener(){
        MyOnclickListener listener = new MyOnclickListener();
        rankingSaler.setOnClickListener(listener);
        totalMoneySaler.setOnClickListener(listener);
        recordSaler.setOnClickListener(listener);
        accountSaler.setOnClickListener(listener);
    }

    private class MyOnclickListener implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.saler_ranking_rel:
                    intent = new Intent(getActivity(), SaleRankingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.saler_money_rel:
                    intent = new Intent(getActivity(), SalesAmountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.saler_record_rel:
                    intent = new Intent(getActivity(), DeliveryNoteMouthQueryActivity.class);
                    startActivity(intent);
                    break;
                case R.id.saler_account_rel:
                    intent = new Intent(getActivity(), SaleAccountListActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
