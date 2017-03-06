package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.config.ViewHolder;
import com.bangware.shengyibao.customer.model.entity.CustomerImage;
import com.bangware.shengyibao.customer.view.ShowImageViewActivity;
import com.bangware.shengyibao.utils.CommonUtil;
import com.bumptech.glide.Glide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class CustomerImageShowAdapter extends BaseAdapter {
	private List<CustomerImage> imageList;
	private Context context;
	private LayoutInflater mInflater;
	private boolean candelete; //是否可删除
	public CustomerImageShowAdapter(Context context, List<CustomerImage> imageList, boolean candelete) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.imageList=imageList;
		this.candelete=candelete;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.media_item_camera, null);
		}
		final ImageView imageshow = ViewHolder.get(convertView, R.id.caremaImage);
		
		imageshow.setImageResource(R.drawable.no_pic_300);
		String img_url = imageList.get(position).getImg_url();
		if (img_url != null && !"".equals(img_url)) {
			img_url = Model.HTTPURL+img_url;
			Glide.with(context).load(img_url).placeholder(R.drawable.no_pic_300).error(R.drawable.no_pic_300)
			.into(imageshow);
		} else {
			Log.d("TAG", "无图片");
		}
		
		//点击查看照片大图
		imageshow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,ShowImageViewActivity.class);
				intent.putExtra("showImage", imageList.get(position).getImg_url());
				context.startActivity(intent);
			}
		});
		
		if (candelete) {
			//长按弹出操作选项
			imageshow.setOnLongClickListener(new OnLongClickListener() {
				public boolean onLongClick(View arg0) {
					Dialog dialog = new AlertDialog.Builder(context)
					.setTitle("操作选项")
					.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							//删除
							case 0:
								Dialog deleteDialog = new AlertDialog.Builder(context)
								.setTitle("提示")
								.setMessage("您确定要删除吗？")
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										//删除原文件
										CommonUtil.deleteFile(Model.HTTPURL+ imageList.get(position).getImg_url());
										//刷新列表
										imageList.remove(position).getImg_url();
										CustomerImageShowAdapter.this.notifyDataSetChanged();
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})
								.create();
								deleteDialog.show();
								break;
							}
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.create();
					dialog.show();
					return true;
				}
			});
		}
		return convertView;
	}

}
