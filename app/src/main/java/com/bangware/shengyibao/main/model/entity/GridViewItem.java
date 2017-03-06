package com.bangware.shengyibao.main.model.entity;


/**
 * GridView Item模型
 * @author luming.tang
 *
 */
public class GridViewItem {

	private int image;//图片
	private String text;//文字
	private String activity;//打开的Activity名称
	
	
	public GridViewItem(int image, String text, String activity){
		this.image =image;
		this.text = text;
		this.activity =activity;
	}
	
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
}
