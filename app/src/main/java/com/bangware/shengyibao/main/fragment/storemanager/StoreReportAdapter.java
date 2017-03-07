package com.bangware.shengyibao.main.fragment.storemanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.main.fragment.storemanager.entity.StockProductBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangware on 2017/3/7.
 */

public class StoreReportAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;// 容器，找到数据所在位置
    private ArrayList<StockProductBean> goodReportList;

    public StoreReportAdapter(Context context,ArrayList<StockProductBean> mGoodReportList) {
        this.context = context;
        this.goodReportList = mGoodReportList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.e("TAG", "getCount: "+goodReportList.size());
        return goodReportList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.e("TAG", "getCount: "+goodReportList.get(position));
        return goodReportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_store_report_list, null);
        }

        TextView name = ViewHolder.get(convertView,R.id.goodName_text);
        TextView count = ViewHolder.get(convertView,R.id.stockCount_text);
        TextView money = ViewHolder.get(convertView,R.id.stockMoney_text);
        TextView proportion = ViewHolder.get(convertView,R.id.stockProportion_text);

        name.setText(goodReportList.get(position).getGoodName());
        count.setText(goodReportList.get(position).getGoodCount());
        money.setText(goodReportList.get(position).getGoodMoney().toString());
        proportion.setText(String.valueOf(goodReportList.get(position).getGoodPercent()));
        return convertView;
    }
}
