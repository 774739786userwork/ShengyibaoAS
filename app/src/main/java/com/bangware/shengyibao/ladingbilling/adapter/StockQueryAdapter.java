package com.bangware.shengyibao.ladingbilling.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.model.Product;

import java.util.List;

/**
 * 余货数据源
 * Created by bangware on 2016/8/22.
 */
public class StockQueryAdapter extends BaseAdapter{
    private List<Product> products_stocklist;
    private Context context;
    private LayoutInflater inflater;

    public StockQueryAdapter(Context context,List<Product> products_stocklist) {
        // TODO Auto-generated constructor stub
        super();
        this.context=context;
        this.products_stocklist=products_stocklist;
    }
    @Override
    public int getCount() {
        return products_stocklist.size();
    }

    @Override
    public Object getItem(int position) {
        return products_stocklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_stock_query, null);
        }
        TextView product_name = ViewHolder.get(convertView, R.id.productName_textview);
        TextView product_specifications = ViewHolder.get(convertView, R.id.specifications_textview);
        TextView product_stock = ViewHolder.get(convertView, R.id.stock_count_textview);
        LinearLayout stock_linearlayout = ViewHolder.get(convertView,R.id.stock_linearlayout);
        LinearLayout name_linearlayout = ViewHolder.get(convertView,R.id.name_linearlayout);
        if (products_stocklist.get(position).getStock() > 0){
            product_name.setText(products_stocklist.get(position).getName());
            product_specifications.setText(products_stocklist.get(position).getSpecifications());
            product_stock.setText(String.valueOf(products_stocklist.get(position).getStock())+products_stocklist.get(position).getUnit());
        }else {
            name_linearlayout.setVisibility(View.GONE);
            stock_linearlayout.setVisibility(View.GONE);
        }
        return convertView;
    }
}
