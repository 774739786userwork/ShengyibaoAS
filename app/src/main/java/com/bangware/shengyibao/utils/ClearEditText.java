package com.bangware.shengyibao.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.bangware.shengyibao.activity.R;


/**
 * 自定义输入框实现类
 * @author ccssll
 *
 */
public class ClearEditText extends EditText implements OnFocusChangeListener,
		TextWatcher {

	/**
	 * 删除按钮的引用
	 * @param context
	 */
	private Drawable mClearDrawable;
	
	public ClearEditText(Context context) {
		this(context,null);
	}
	
	//写这个构造方法是为了防止很多熟悉不能再XML里面定义
	public ClearEditText(Context context, AttributeSet attrs) {
	    this(context, attrs, android.R.attr.editTextStyle); 
	} 
	
	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    init();
	}

	private void init(){
		//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
		mClearDrawable=getCompoundDrawables()[2];
		if(mClearDrawable==null){
			mClearDrawable=getResources().getDrawable(R.drawable.emotionstore_progresscancelbtn);
		}
		mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());
		setClearIconVisible(false); 
        setOnFocusChangeListener(this); 
        addTextChangedListener(this); 
	}
	
	//记录手指按下的位置来确定点击图标
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(getCompoundDrawables()[2]!=null){
			if(event.getAction()== MotionEvent.ACTION_UP){
				boolean touchable = event.getX() > (getWidth() 
                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) 
                        && (event.getX() < ((getWidth() - getPaddingRight())));
				if(touchable){
					this.setText("");
				}
			}
		}
		return super.onTouchEvent(event);
	}
	
	//当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			setClearIconVisible(getText().length()>0);
		}else{
			setClearIconVisible(false);
		}
		
	}
	
	//设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
	protected void setClearIconVisible(boolean visible) {
		Drawable right=visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0], 
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}
	
	//当输入框里面内容发生变化时的回调方法
	@Override
    public void onTextChanged(CharSequence s, int start, int count,
							  int after) {
        setClearIconVisible(s.length() > 0); 
    } 

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	 /**
     * 设置晃动动画
     */
    public void setShakeAnimation(){
    	this.setAnimation(shakeAnimation(5));
    }
    
    
    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts){
    	Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
    	translateAnimation.setInterpolator(new CycleInterpolator(counts));
    	translateAnimation.setDuration(1000);
    	return translateAnimation;
    }


}
