package com.bangware.shengyibao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


/**
 * 底部弹出菜单   
 * @author ccssll
 *
 */
public class RemarkPopupWindow extends BaseActivity implements OnClickListener {
	private Button btn_sure;
	private EditText remark_et;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);
		
		remark_et=(EditText) this.findViewById(R.id.remark_et);
		btn_sure=(Button) this.findViewById(R.id.btn_sure);
		layout=(LinearLayout) findViewById(R.id.pop_layout);
		
		//添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
               /* Toast.makeText(getApplicationContext(), "点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();*/
            }
        });
        //添加按钮监听
        btn_sure.setOnClickListener(this);
        
	}
	
	 //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
//        finish();
        return true;
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.btn_sure:
        	Intent intent = new Intent();
			intent.putExtra("remark", remark_et.getText().toString());
			setResult(1001,intent);
            break;
        default:
            break;
		}
		finish();
	}
	

}
