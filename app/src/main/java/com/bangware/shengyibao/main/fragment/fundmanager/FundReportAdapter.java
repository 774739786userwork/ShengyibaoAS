package com.bangware.shengyibao.main.fragment.fundmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.main.fragment.fundmanager.entity.FunkBean;

import java.util.ArrayList;

/**
 * Created by bangware on 2017/3/8.
 */

public class FundReportAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;// 容器，找到数据所在位置
    private ArrayList<FunkBean> funkReportList;

    public FundReportAdapter(Context context, ArrayList<FunkBean> funkReportList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.funkReportList = funkReportList;
    }

    @Override
    public int getCount() {
        return funkReportList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_fund_report_list, null);
        }
        ImageView iv = ViewHolder.get(convertView,R.id.pay_imageview);
        TextView name = ViewHolder.get(convertView,R.id.payname_text);
        TextView totalsum = ViewHolder.get(convertView,R.id.paytotalsum_text);

        iv.setImageResource(funkReportList.get(position).getImg());
        name.setText(funkReportList.get(position).getPayName());
        totalsum.setText(funkReportList.get(position).getPayMoney());
        return convertView;
    }
}
