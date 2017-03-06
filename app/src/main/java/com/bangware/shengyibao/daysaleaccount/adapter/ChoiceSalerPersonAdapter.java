package com.bangware.shengyibao.daysaleaccount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.daysaleaccount.model.entity.ChoicePersonBean;

import java.util.List;

/**
 *记量选择业务员数据适配器
 * Created by bangware on 2016/9/1.
 */
public class ChoiceSalerPersonAdapter extends BaseAdapter implements SectionIndexer{
    private List<ChoicePersonBean> list=null;
    private Context mContext;

    public ChoiceSalerPersonAdapter(Context mContext,List<ChoicePersonBean> list){
        this.mContext=mContext;
        this.list=list;
    }
    //当listview数据发生变化时，调用此方法更新listview
    public void updateListView(List<ChoicePersonBean> list) {
        // TODO Auto-generated method stub
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        final ChoicePersonBean mContent=list.get(position);
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_choice_saler_person, null);
            viewHolder.tvLetter=(TextView) convertView.findViewById(R.id.catalog);
            viewHolder.tvTitle=(TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        viewHolder.tvTitle.setText(this.list.get(position).getName());
        return convertView;
    }

    final static class ViewHolder{
        TextView tvLetter;
        TextView tvTitle;
    }

    @Override
    public Object[] getSections() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
}
