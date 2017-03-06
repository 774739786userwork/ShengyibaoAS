package com.bangware.shengyibao.customer.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Constants_Camera;
import com.bangware.shengyibao.customer.view.ShowImageActivity;
import com.bangware.shengyibao.utils.CommonUtil;


/**
 * 多张照片拍摄
 * @author ccssll
 *
 */
public class CaremaAdapter extends BaseAdapter {
	private Context context;
	private int screenWidth; //屏幕宽度
	private List<String> listCaremaName; //照片绝对路径
	private boolean candelete; //是否可删除
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listCaremaName.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return listCaremaName.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int index, View view, ViewGroup arg2) {
		ViewHolder holder;
		if(view == null){
			view = LayoutInflater.from(context).inflate(R.layout.media_item_camera, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView)view.findViewById(R.id.caremaImage);
			
			int imageWidth = screenWidth / Constants_Camera.MAX_NUM_COLUMNS;
			int imageHeight = imageWidth * 4 / 3;
			
			LayoutParams lparam = new LayoutParams(imageWidth, imageHeight);
			holder.imageView.setLayoutParams(lparam);	
			view.setTag(holder);
		}else{
			holder = (ViewHolder)view.getTag();
		}
		holder.imageView.setImageBitmap(CommonUtil.getBitmapInLocal(getItem(index)));
		//点击查看详细
		holder.imageView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(context,ShowImageActivity.class);
				intent.putExtra("ImageUrl", listCaremaName.get(index));
				context.startActivity(intent);
			}
		});
		
		if(candelete){
			//长按弹出操作选项
			holder.imageView.setOnLongClickListener(new OnLongClickListener() {
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
										CommonUtil.deleteFile(getItem(index));
										//刷新列表
										listCaremaName.remove(index);
										CaremaAdapter.this.notifyDataSetChanged();
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
		
		return view;
	}
	
	public CaremaAdapter(Context context, int screenWidth, List<String> listCaremaName ,boolean candelete) {
		super();
		this.context = context;
		this.screenWidth = screenWidth;
		this.listCaremaName = listCaremaName;
		this.candelete = candelete;
	}

	private class ViewHolder{
		ImageView imageView;
	}
	
	private void remove(String imagePath){
		
	}

}
