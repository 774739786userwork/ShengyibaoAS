package com.bangware.shengyibao.returngood.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;
import com.jauker.widget.BadgeView;

import java.util.List;

/**
 * 基本功能：左侧Adapter
 * 创建时间：16/4/14
 * 邮箱：w489657152@gmail.com
 */
public class LeftListAdapter extends BaseAdapter {
//    private String[] leftStr; String[] leftStr
    boolean[] flagArray;
    private List<ProductsTypes> productsList;
    private Context context;

    private BadgeView quantityIcon;
    public LeftListAdapter(Context context, List<ProductsTypes> productsList, boolean[] flagArray) {
        this.productsList = productsList;
        this.context = context;
        this.flagArray = flagArray;
    }

    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return productsList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder = null;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.left_list_item, null);
            holder.left_list_item = (TextView) arg1.findViewById(R.id.left_list_item);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }

        /*quantityIcon = new BadgeView(context.getApplicationContext());
        quantityIcon.setTargetView(arg1.findViewById(R.id.quantityIcon));
        quantityIcon.setBadgeGravity(Gravity.RIGHT);
        quantityIcon.setTextSize(8.5f);
        quantityIcon.setBadgeCount(112);*/

        holder.updataView(arg0);
        return arg1;
    }

    private class Holder {
        private TextView left_list_item;

        public void updataView(final int position) {
            left_list_item.setText(productsList.get(position).getKindName());
            if (flagArray[position]) {
                left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));
            } else {
                left_list_item.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }
}
