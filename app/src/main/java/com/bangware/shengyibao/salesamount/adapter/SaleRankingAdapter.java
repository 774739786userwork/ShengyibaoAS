package com.bangware.shengyibao.salesamount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.salesamount.model.entity.SaleRankingBean;
import com.bangware.shengyibao.utils.NumberUtils;

import java.util.List;

/**
 * Created by ccssll on 2016/8/10.
 */
public class SaleRankingAdapter extends BaseAdapter{
    private Context context;// 上下文内置对象
    private LayoutInflater mInflater;// 容器，找到数据所在位置
    private List<SaleRankingBean> list;

    public SaleRankingAdapter(Context context,List<SaleRankingBean> list){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
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
            convertView = mInflater.inflate(R.layout.item_saleranking_list, null);
        }

        TextView sale_person = ViewHolder.get(convertView,R.id.sale_person_tv);
        TextView sale_ranking = ViewHolder.get(convertView,R.id.sale_ranking_tv);
        TextView customer_quantity = ViewHolder.get(convertView,R.id.customer_quantity_tv);
        TextView total_sum = ViewHolder.get(convertView,R.id.sale_totalsum_tv);

        sale_person.setText(list.get(position).getSalerPerson().toString());
        sale_ranking.setText(list.get(position).getSaleRanking().toString());
        customer_quantity.setText(list.get(position).getCustomerQuantity().toString()+"家");
        total_sum.setText("¥"+String.valueOf(NumberUtils.toDoubleDecimal(list.get(position).getSaleTotalSum())));
        return convertView;
    }
}
