package com.bangware.shengyibao.customer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.customer.model.entity.Customer;
import com.bangware.shengyibao.utils.NumberUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 附近客户查询数据源展示
 * Created by ccssll on 2016/8/3.
 */
public class QueryNearByCustomerAdapter extends BaseAdapter{
    private Context context;// 上下文内置对象
    private LayoutInflater mInflater;
    private List<Customer> customerList = null;
    public QueryNearByCustomerAdapter(Context context,List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // 适配器中所代表的数据集中的条目数
        return customerList.size();
    }

    @Override
    public Object getItem(int position) {
        // 获取数据集中与指定索引对应的数据项
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // 取在列表中与指定索引对应的行id
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_querynearby_customer, null);
        }
        ImageView customer_img = ViewHolder.get(convertView,R.id.nearby_customer_image);
        TextView customername_tv = ViewHolder.get(convertView,R.id.customerNameTextView);
        TextView contactname_tv = ViewHolder.get(convertView,R.id.contact_name_textview);
        TextView contactmobile_tv = ViewHolder.get(convertView, R.id.contact_mobile_textview);
        TextView last_deliverydate = ViewHolder.get(convertView,R.id.customer_last_deliverydate_textview);
        TextView address_tv = ViewHolder.get(convertView,R.id.customer_address_textview);
        TextView metres_tv = ViewHolder.get(convertView,R.id.nearby_metres_textview);

        customer_img.setImageResource(R.drawable.no_pic_300);
        if(customerList.get(position).getImages().size() > 0){
            String img_url = customerList.get(position).getImages().get(0).getImg_url();
            if (img_url != null && !"".equals(img_url)) {
                img_url = Model.HTTPURL+img_url;
                //谷歌推荐的图片加载库  用于显示图片
                Glide.with(context).load(img_url).placeholder(R.drawable.no_pic_300).error(R.drawable.no_pic_300)
                        .into(customer_img);
            } else {
                Log.d("TAG", "无图片");
            }
        }

        customername_tv.setText(customerList.get(position).getName().toString());
        contactname_tv.setText(customerList.get(position).getName().toString().trim());
        if (customerList.get(position).getContacts().size() > 0) {
            contactname_tv.setText(customerList.get(position).getContacts().get(0).getName().toString().trim());
            contactmobile_tv.setText(customerList.get(position).getContacts().get(0).getMobile1().toString().trim());
        }
        address_tv.setText(customerList.get(position).getAddress().toString());

        last_deliverydate.setText(customerList.get(position).getLasttime());
        if(!last_deliverydate.getText().toString().isEmpty() && !last_deliverydate.getText().toString().equals("null")){
            last_deliverydate.setText("上次进货日期："+customerList.get(position).getLasttime());
        }else{
            last_deliverydate.setText("暂无进货");
        }

        metres_tv.setText(customerList.get(position).getNearBy());
        if(!metres_tv.getText().toString().isEmpty() && !metres_tv.getText().toString().equals("null")){
            String distance = customerList.get(position).getNearBy().toString();
            String newdistance = NumberUtils.toDouble(distance) > 1000 ? (NumberUtils.toDouble(distance) / 1000) + "km" : distance + "m";
            metres_tv.setText(newdistance);
        }else{
            metres_tv.setText("");
        }
        convertView.setBackgroundResource(R.drawable.my_tab_background);// 设置listview点击的背景色
        return convertView;
    }
}
