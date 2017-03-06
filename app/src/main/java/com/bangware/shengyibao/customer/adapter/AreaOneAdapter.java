package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.customer.model.entity.RegionalArea;
import com.bangware.shengyibao.customer.view.CustomerActivity;

public class AreaOneAdapter extends BaseAdapter {
	private Context context;
	private List<RegionalArea> regionalArealist;
	private LayoutInflater inflater;
	private int pos = 0;
	
	public AreaOneAdapter(Context context, List<RegionalArea> regionalArealist) {
		super();
		this.context = context;
		this.regionalArealist = regionalArealist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return regionalArealist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return regionalArealist.get(position);
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
			convertView = inflater.inflate(R.layout.item_areamore_list, null);
		}
		TextView areaType = ViewHolder.get(convertView, R.id.area_type_textview);
		TextView areaId = ViewHolder.get(convertView,R.id.area_type_id);
		LinearLayout typeLayout = ViewHolder.get(convertView, R.id.More_list_typeLayout);
		
		String areaStr = regionalArealist.get(position).getRegional_name();
		areaType.setText(areaStr);
		String areaIdStr = regionalArealist.get(position).getRegional_id();
		areaId.setText(areaIdStr);
		
		typeLayout.setBackgroundResource(R.drawable.search_more_mainlistselect);
		if (position == pos) {
			typeLayout.setBackgroundResource(R.drawable.list_bkg_line_u);
		}
		
		return convertView;
	}
	
	public void setSelectItem(int i) {
		pos = i;
	}

	public int getSelectItem() {
		return pos;
	}

}
