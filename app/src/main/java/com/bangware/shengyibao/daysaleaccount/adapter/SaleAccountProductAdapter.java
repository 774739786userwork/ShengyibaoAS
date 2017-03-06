package com.bangware.shengyibao.daysaleaccount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.daysaleaccount.model.entity.SaleAccountProductBean;
import com.bangware.shengyibao.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by ccssll on 2016/8/9.
 */
public class SaleAccountProductAdapter extends BaseAdapter{
    private Context context;// 上下文内置对象
    private LayoutInflater mInflater;// 容器，找到数据所在位置
    private List<SaleAccountProductBean> list;

    public SaleAccountProductAdapter(Context context, List<SaleAccountProductBean> list){
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
            convertView = mInflater.inflate(R.layout.item_daysale_productlist, null);
        }

        TextView product_name = ViewHolder.get(convertView,R.id.product_tv_name);
        TextView sale_volume_price = ViewHolder.get(convertView,R.id.saleVolume_pricetext);
        TextView foregift = ViewHolder.get(convertView,R.id.foregift_text);
        TextView person_saleQuantity = ViewHolder.get(convertView,R.id.person_saleQuantity);
        TextView giftVolume = ViewHolder.get(convertView,R.id.giftVolume_tv);
        TextView daytotalsum = ViewHolder.get(convertView,R.id.daySale_totalSum);

        Product product = list.get(position).getProduct();

        product_name.setText(product.getName());
        sale_volume_price.setText("¥"+String.valueOf(product.getPrice())+" X "+ String.valueOf(list.get(position).getSalesVolume()));
        foregift.setText(String.valueOf(product.getForegift()));
        person_saleQuantity.setText(list.get(position).getSaleperson_amount().toString());
        giftVolume.setText(String.valueOf(list.get(position).getGiftsVolume()));
        daytotalsum.setText("¥"+String.valueOf(list.get(position).getProduct_subtotal_sum()));
        return convertView;
    }
}
