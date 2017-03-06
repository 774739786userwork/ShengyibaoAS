package com.bangware.shengyibao.daysaleaccount.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.daysaleaccount.model.entity.ProductInfoItemBean;
import com.bangware.shengyibao.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 新建日销售清单ExpandListView的适配器
 * Created by bangware on 2016/8/31.
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> groupTitle;
    //子项是一个map，key是group的id，每一个group对应一个ChildItem的list
    private Map<Integer, List<ProductInfoItemBean>> childMap;
    private int index = -1;

    public MyBaseExpandableListAdapter(Context context, List<String> groupTitle, Map<Integer, List<ProductInfoItemBean>> childMap) {
        this.mContext = context;
        this.groupTitle = groupTitle;
        this.childMap = childMap;
    }
    /*
     *  Gets the data associated with the given child within the given group
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //我们这里返回一下每个item的名称，以便单击item时显示
        return childMap.get(groupPosition).get(childPosition).getSaleperson_amount();
    }
    /*
     * 取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    /*
     *  Gets a View that displays the data for the given child within the given group
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_saleaccount_product_info, null);
        }
        TextView sale_volume_price = ViewHolder.get(convertView,R.id.saleVolume_pricetext_newsale);
        TextView foregift = ViewHolder.get(convertView,R.id.foregift_text_newsale);
        TextView giftVolume = ViewHolder.get(convertView,R.id.giftVolume_tv_newsale);
        TextView daytotalsum = ViewHolder.get(convertView,R.id.totalSum_newsale);
        TextView salecount = ViewHolder.get(convertView,R.id.count_textview);
        final LinearLayout lin = ViewHolder.get(convertView,R.id.space_add_view);
        Product product = childMap.get(groupPosition).get(childPosition).getProduct();

        sale_volume_price.setText(String.valueOf(childMap.get(groupPosition).get(childPosition).getSalesVolume())+" X "+ String.valueOf(product.getPrice()));
        foregift.setText(String.valueOf(product.getForegift()));
        giftVolume.setText(String.valueOf(childMap.get(groupPosition).get(childPosition).getGiftsVolume()));
        daytotalsum.setText("¥"+String.valueOf(childMap.get(groupPosition).get(childPosition).getProduct_subtotal_sum()));


        salecount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext).inflate(
                        R.layout.test_count_file, null).findViewById(R.id.contactslayout);
                lin.addView(layout);
                EditText edit_person = (EditText) layout.findViewById(R.id.ed_one);
                edit_person.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                            index=childPosition;
                        }
                        return false;
                    }
                });

                edit_person.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        EditText et=(EditText) view;
                        myWatcher mWatcher = null;
                        if(mWatcher==null){
                            mWatcher=new myWatcher();
                        }
                        if(hasFocus){
                            et.addTextChangedListener(mWatcher);//设置edittext内容监听
                        }else {
                            et.removeTextChangedListener(mWatcher);
                        }
                    }
                });
                edit_person.clearFocus();//防止点击以后弹出键盘，重新getview导致的焦点丢失;/
                if (index != -1 && index == childPosition){
                    // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
                    edit_person.requestFocus();
                }
                edit_person.setSelection(edit_person.getText().length());

                TextView delete =(TextView) layout.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lin.removeView(layout);
                    }
                });
            }
        });


        return convertView;
    }

    /*
     * 取得指定分组的子元素数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return childMap.get(groupPosition).size();
    }

    /**
     * 取得与给定分组关联的数据
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupTitle.get(groupPosition);
    }

    /**
     * 取得分组数
     */
    @Override
    public int getGroupCount() {
        return groupTitle.size();
    }

    /**
     * 取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /*
     *头部父容器
     *return: the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_saleaccount_product, null);
        }
        TextView name = ViewHolder.get(convertView,R.id.group_name_textview);
        name.setText(groupTitle.get(groupPosition));

        TextView stock = ViewHolder.get(convertView,R.id.product_stock_textview);
        stock.setText("余：10包");

        TextView takegoods = ViewHolder.get(convertView,R.id.take_goods);
        takegoods.setText("提：80包");
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // Indicates whether the child and group IDs are stable across changes to the underlying data
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // Whether the child at the specified position is selectable
        return true;
    }

    class myWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            editable.toString();
        }
    }
}
