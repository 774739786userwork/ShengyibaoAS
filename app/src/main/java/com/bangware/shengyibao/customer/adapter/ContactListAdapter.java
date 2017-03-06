package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangware.shengyibao.customer.model.entity.Contacts;
import com.bangware.shengyibao.activity.R;

/**
 * 客户详情页适配器
 * @author ccssll
 *
 */
public class ContactListAdapter extends BaseAdapter {
	private List<Contacts> contactList;
	private Context context;
	private LayoutInflater mInflater;
	private Callback_detail callback;
	private LinearLayout linearLayout;
	/**
	 * listview点击事件接口
	 * @author ccssll
	 *
	 */
	public interface Callback_detail {
	    void onClick(View item, View widget, int position, int which);
	}
	//上下文对象
	public ContactListAdapter(Context context,List<Contacts> contactList, Callback_detail callback) {
		this.contactList = contactList;
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.callback = callback;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contactList.size();
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
			convertView = mInflater.inflate(R.layout.item_contacts, null);

			holder.contact_editor=(TextView) convertView.findViewById(R.id.editor_tv);
			holder.contactMobile = (TextView) convertView.findViewById(R.id.contact_userMobile);
			holder.contactMobile_two = (TextView) convertView.findViewById(R.id.contact_userMobile_two);
			holder.phone_img = (ImageView) convertView.findViewById(R.id.phone_img);
			holder.msg_img = (ImageView) convertView.findViewById(R.id.msg_img);
			holder.msg_img_two = (ImageView) convertView.findViewById(R.id.msg_img_two);
			holder.phone_img_two = (ImageView) convertView.findViewById(R.id.phone_img_two);
			holder.main_customer= (ImageView) convertView.findViewById(R.id.main_customer);
			int p=position+1;
			if (p==1) {
				holder.contactName = (TextView) convertView.findViewById(R.id.contact_userName);
				holder.main_customer.setVisibility(View.VISIBLE);
			}else
			{
				holder.contactName = (TextView) convertView.findViewById(R.id.contact_userName);
			}
		
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
			String nameStr = contactList.get(position).getName();
			holder.contactName.setText(nameStr);
			
			String mobileStr = contactList.get(position).getMobile1();
			holder.contactMobile.setText(mobileStr);
			
			String mobileTwoStr = contactList.get(position).getMobile2();
			holder.contactMobile_two.setText(mobileTwoStr);
			if (holder.contactName.getText().toString().equals("")||holder.contactMobile.getText().toString().equals("null")) {
				holder.contactName.setVisibility(View.GONE);
			}else{
				holder.contactName.setVisibility(View.VISIBLE);
			}
			if (holder.contactMobile.getText().toString().equals("")||holder.contactMobile.getText().toString().equals("null")) {
				holder.contactMobile.setVisibility(View.GONE);
				holder.phone_img.setVisibility(View.GONE);
				holder.msg_img.setVisibility(View.GONE);
			}else
			{
				holder.contactMobile.setVisibility(View.VISIBLE);
				holder.phone_img.setVisibility(View.VISIBLE);
				holder.msg_img.setVisibility(View.VISIBLE);
			}
			if (holder.contactMobile_two.getText().toString().equals("")||holder.contactMobile_two.getText().toString().equals("null")) {
				holder.contactMobile_two.setVisibility(View.GONE);
				holder.phone_img_two.setVisibility(View.GONE);
				holder.msg_img_two.setVisibility(View.GONE);
			}else
			{
				holder.contactMobile_two.setVisibility(View.VISIBLE);
				holder.phone_img_two.setVisibility(View.VISIBLE);
				holder.msg_img_two.setVisibility(View.VISIBLE);
			}
			
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
			final int p3=position;
			final int name=holder.contact_editor.getId();
			holder.contact_editor.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					callback.onClick(view, parent, p3, name);
				}
			});
			//调用发送短信点击事件
			final int two2 = holder.msg_img_two.getId();
			holder.msg_img_two.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onClick(view, parent, p2, two2);
				}
			});
		return convertView;
	} 
	

	//ViewHolder静态类
	static class ViewHolder{
		public TextView contactName,contactMobile,contactMobile_two,contact_editor;
		public ImageView phone_img,msg_img,phone_img_two,msg_img_two,main_customer;
	}

}
