package com.bangware.shengyibao.customer.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.customer.model.entity.DistanceType;

/**
 * 右边地区数据源
 */
public class AreaTwoAdapter extends BaseAdapter {
	private Context context;
	private DistanceType[] text;
	private LayoutInflater inflater;
	private int pos = 0;
	
	public AreaTwoAdapter(Context context,DistanceType[] text) {
		super();
		this.context = context;
		this.text = text;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return text[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.item_areatwo_list, null);
		}
		TextView choiceType = ViewHolder.get(convertView, R.id.Search_Area_listitem_txt);
		LinearLayout Area_twoList_Layout = ViewHolder.get(convertView, R.id.Area_twoList_Layout);
		String areaStr = text[position].getDistance_name();
		choiceType.setText(areaStr);

		TextView distanceId = ViewHolder.get(convertView,R.id.Search_AreaId_listitem_txt);
		String idStr = text[position].getDistance_id();
		distanceId.setText(idStr);

		Area_twoList_Layout.setBackgroundResource(R.drawable.my_list_txt_background);
		choiceType.setTextColor(Color.parseColor("#FF666666"));
		if (position == pos) {
			Area_twoList_Layout.setBackgroundResource(R.drawable.search_more_morelisttop_bkg);
			choiceType.setTextColor(Color.parseColor("#FFFF8C00"));
		}
		return convertView;
	}
	
	public void setSelectItem(int i) {
		pos = i;
	}

}
