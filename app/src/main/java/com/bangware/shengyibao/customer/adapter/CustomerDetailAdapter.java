package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangware.shengyibao.customer.model.entity.CustomerInfo;
import com.bangware.shengyibao.customer.view.CustomerDetailActivity;
import com.bangware.shengyibao.activity.R;

/**
 * 客户详情页适配器
 * @author ccssll
 *
 */
public class CustomerDetailAdapter extends BaseAdapter {
	private List<CustomerInfo> conList;
	private Context context;
	private LayoutInflater mInflater;
	private Callback_detail callback;
	
	/**
	 * listview点击事件接口
	 * @author ccssll
	 *
	 */
	public interface Callback_detail {
	    void onClick(View item, View widget, int position, int which);
	}
	//上下文对象
	public CustomerDetailAdapter(Context context,List<CustomerInfo> conList, Callback_detail callback) {
		this.conList = conList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.callback = callback;
	}
	public CustomerDetailAdapter(CustomerDetailActivity myCustomerList) {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return conList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		 ViewHolder holder = null;
		//判断缓存convertView是否为空，如果为空，则需要创建view对象
		if(convertView == null){
			holder = new ViewHolder();
			//加载定义好的item布局文件
			convertView = mInflater.inflate(R.layout.item_customer_detail, null);
			holder.link_man = (TextView) convertView.findViewById(R.id.link_man);
			holder.tel_tv = (TextView) convertView.findViewById(R.id.tel_tv);
			holder.tel_two_tv = (TextView) convertView.findViewById(R.id.tel_two_tv);
			holder.phone_img = (ImageView) convertView.findViewById(R.id.phone_img);
			holder.msg_img = (ImageView) convertView.findViewById(R.id.msg_img);
			holder.phone_img_two = (ImageView) convertView.findViewById(R.id.phone_img_two);
			holder.msg_img_two = (ImageView) convertView.findViewById(R.id.msg_img_two);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
//			holder.link_man.setText((String) conList.get(position).getList_contact().get(0).getName());
//			holder.tel_tv.setText((String) conList.get(position).getList_contact().get(0).getMobile1());
//			holder.tel_two_tv.setText((String)conList.get(position).getList_contact().get(0).getMobile2());
//			
//			if(!(holder.tel_tv.getText().toString()).isEmpty()){
//				holder.tel_tv.setVisibility(View.VISIBLE);
//				holder.phone_img.setVisibility(View.VISIBLE);
//				holder.msg_img.setVisibility(View.VISIBLE);
//				holder.tel_tv.setText((String)conList.get(position).getList_contact().get(0).getMobile1());
//				/*holder.link_man.setVisibility(View.VISIBLE);
//				holder.link_man.setText((String) conList.get(position).getName());*/
//				
//				if(!(holder.tel_two_tv.getText().toString()).isEmpty()){
//					holder.tel_two_tv.setVisibility(View.VISIBLE);
//					holder.phone_img_two.setVisibility(View.VISIBLE);
//					holder.msg_img_two.setVisibility(View.VISIBLE);
//					holder.tel_two_tv.setText((String)conList.get(position).getList_contact().get(0).getMobile2());
//				}else{
//					holder.tel_two_tv.setVisibility(View.GONE);
//					holder.phone_img_two.setVisibility(View.GONE);
//					holder.msg_img_two.setVisibility(View.GONE);
//				}
//				
//			}else {
//				holder.tel_tv.setVisibility(View.GONE);
//				holder.phone_img.setVisibility(View.GONE);
//				holder.msg_img.setVisibility(View.GONE);
//				
//				if(!(holder.tel_two_tv.getText().toString()).isEmpty()){
//					holder.tel_tv.setVisibility(View.VISIBLE);
//					holder.phone_img.setVisibility(View.VISIBLE);
//					holder.msg_img.setVisibility(View.VISIBLE);
//					holder.tel_tv.setText((String)conList.get(position).getList_contact().get(0).getMobile2());
//				}else{
//					holder.tel_tv.setVisibility(View.GONE);
//					holder.phone_img.setVisibility(View.GONE);
//					holder.msg_img.setVisibility(View.GONE);
//				}
//			}
//			
			
			//调用拨打电话点击事件
			final View view = convertView;
	        final int p = position;
	        final int one = holder.phone_img.getId();
			holder.phone_img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onClick(view, parent, p, one);
				}
			});
			//调用发送短信点击事件
			final int two = holder.msg_img.getId();
			holder.msg_img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					callback.onClick(view, parent, p, two);
				}
			});
			
			final int p2 = position;
	        final int one2 = holder.phone_img_two.getId();
			holder.phone_img_two.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onClick(view, parent, p2, one2);
				}
			});
			//调用发送短信点击事件
			final int two2 = holder.msg_img_two.getId();
			holder.msg_img_two.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					callback.onClick(view, parent, p2, two2);
				}
			});
		return convertView;
	}
	

		//ViewHolder静态类
		static class ViewHolder{
			public TextView link_man,tel_tv,tel_two_tv;
			public ImageView phone_img,msg_img,phone_img_two,msg_img_two;
		}

}
