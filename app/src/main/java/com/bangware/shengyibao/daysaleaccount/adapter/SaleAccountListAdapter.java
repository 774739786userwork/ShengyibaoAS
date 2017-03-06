package com.bangware.shengyibao.daysaleaccount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountListBean;

import java.util.List;

/**
 * Created by ccssll on 2016/8/11.
 */
public class SaleAccountListAdapter extends BaseAdapter{
    private Context context;// 上下文内置对象
    private LayoutInflater mInflater;// 容器，找到数据所在位置
    private List<SaleAccountListBean> list;

    public SaleAccountListAdapter(Context context,List<SaleAccountListBean> list){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_daysale_list, null);
        }

        TextView sale_date = ViewHolder.get(convertView,R.id.sale_date_tv);
        TextView create_person = ViewHolder.get(convertView,R.id.create_person_tv);
        TextView total_sum = ViewHolder.get(convertView,R.id.total_sum_tv);
        TextView unpaid_sum = ViewHolder.get(convertView,R.id.unpaid_sum_tv);

        TextView bank_card = ViewHolder.get(convertView,R.id.bank_textview);
        TextView wetchat = ViewHolder.get(convertView,R.id.wechat_textview);
        TextView payment = ViewHolder.get(convertView,R.id.payment_textview);
        TextView cash_money = ViewHolder.get(convertView,R.id.cashamount_textview);

        sale_date.setText(list.get(position).getSaledate().toString());
        create_person.setText(list.get(position).getCreateperson().toString());
        total_sum.setText("¥"+String.valueOf(list.get(position).getTotalsum()));
        unpaid_sum.setText("¥"+String.valueOf(list.get(position).getUnpaidmoney()));

        bank_card.setText("¥"+String.valueOf(list.get(position).getBankreceive()));
        wetchat.setText("¥"+String.valueOf(list.get(position).getWetchatreceive()));
        payment.setText("¥"+String.valueOf(list.get(position).getPaymentreceive()));
        cash_money.setText("¥"+String.valueOf(list.get(position).getCashreceive()));
        convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
        return convertView;
    }
}
