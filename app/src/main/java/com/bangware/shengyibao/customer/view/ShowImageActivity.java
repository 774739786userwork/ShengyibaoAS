package com.bangware.shengyibao.customer.view;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bangware.shengyibao.utils.CommonUtil;

/**
 * 点击查看照片详细
 * @author ccssll
 *
 */
public class ShowImageActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//显示传递过来的照片地址
		String imageUrl = getIntent().getStringExtra("ImageUrl");
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(CommonUtil.getBitmapInLocal(imageUrl));
		
		setContentView(iv);
	}
}
