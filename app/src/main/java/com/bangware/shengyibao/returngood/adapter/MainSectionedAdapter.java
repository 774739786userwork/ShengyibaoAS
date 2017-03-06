package com.bangware.shengyibao.returngood.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.model.Product;
import com.bangware.shengyibao.returngood.model.entity.ProductsTypes;
import com.bangware.shengyibao.utils.volley.ImageRequester;

import java.util.List;

/**
 * 基本功能：右侧Adapter
 */
public class MainSectionedAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private List<ProductsTypes> leftStr;
    private Product[][] rightArray;
    private ImageRequester mImageRequester;
    public MainSectionedAdapter(Context context, List<ProductsTypes> leftStr, Product[][] rightStr) {
        this.mContext = context;
        this.leftStr = leftStr;
        this.rightArray = rightStr;
        this.mImageRequester = new ImageRequester(this.mContext, true);
    }

   private class Holder
    {
        public ImageView imageItem;
        public TextView productName_text;
        public TextView product_specifications_text;
        public TextView product_price_text;
    }
    @Override
    public Object getItem(int section, int position) {
        return rightArray[section][position];
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftStr.size();
    }

    @Override
    public int getCountForSection(int section) {
        if(rightArray!=null && section<=rightArray.length) {
            return rightArray[section].length;
        }
        return 0;
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView==null)
        {
            holder=new Holder();
            convertView=View.inflate(mContext,R.layout.right_list_item,null);
            holder.imageItem= (ImageView) convertView.findViewById(R.id.product_imageItem);
            holder.product_price_text= (TextView) convertView.findViewById(R.id.product_price_text);
            holder.product_specifications_text= (TextView) convertView.findViewById(R.id.product_specifications_text);
            holder.productName_text= (TextView) convertView.findViewById(R.id.productName_text);
            convertView.setTag(holder);
        }else
        {
            holder= (Holder) convertView.getTag();
        }
        holder.productName_text.setText(rightArray[section][position].getName());
        holder.product_price_text.setText(String.valueOf(rightArray[section][position].getPrice())+"元/"+rightArray[section][position].getUnit());
        holder.product_specifications_text.setText(rightArray[section][position].getSpecifications());
        String  ImageUrl=rightArray[section][position].getImage();
        holder.imageItem.setImageResource(R.drawable.shop_photo_frame);
        if (ImageUrl != null && !"".equals(ImageUrl)) {
            ImageUrl = Model.PRODUCT_IMAGE_URL+ImageUrl;
            //ImageListener listener = ImageLoader.getImageListener(hold.mImage, R.mipmap.shop_photo_frame, R.mipmap.shop_photo_frame);
            //imageLoader.get(imageURL, listener);
            mImageRequester.load(holder.imageItem, ImageUrl, R.drawable.shop_photo_frame, R.drawable.shop_photo_frame);
        }else{
            Log.e("MainSectionedAdapter",rightArray[section][position].getName()+"图片缺失");
        }
        convertView.setBackgroundResource(R.drawable.my_tab_background);
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(leftStr.get(section).getKindName());
        return layout;
    }

}
