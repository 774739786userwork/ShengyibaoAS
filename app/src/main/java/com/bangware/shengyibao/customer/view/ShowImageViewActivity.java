package com.bangware.shengyibao.customer.view;

import android.os.Bundle;
import android.widget.ImageView;

import com.bangware.shengyibao.activity.BaseActivity;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.config.Model;
import com.bumptech.glide.Glide;


/**
 * 编辑页点击查看图片
 * @author ccssll
 *
 */
public class ShowImageViewActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String customerShowImage = getIntent().getStringExtra("showImage");
		ImageView iv = new ImageView(this);
		if(customerShowImage!=null && !"".equals(customerShowImage)){
			Glide.with(getApplicationContext()).load(Model.HTTPURL+ customerShowImage).placeholder(R.drawable.no_pic_300).error(R.drawable.no_pic_300)
			.into(iv);
	    }
		setContentView(iv);
	}
}
