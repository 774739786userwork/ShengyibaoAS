package com.bangware.shengyibao.returngood.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.returngood.adapter.ShowReturnsGoodsListAdapter;
import com.bangware.shengyibao.returngood.model.entity.ReturnNoteGoods;
import com.bangware.shengyibao.returngood.presenter.ReturnsProductPresenter;
import com.bangware.shengyibao.shopcart.adapter.ShowShopCartGoodsListAdapter;
import com.bangware.shengyibao.shopcart.model.entity.ShopCartGoods;
import com.bangware.shengyibao.shopcart.presenter.ShopCartPresenter;
import com.bangware.shengyibao.utils.customdialog.CustomDialog;

import java.util.ArrayList;
import java.util.List;


public class ReturnGoodPopupWindow extends PopupWindow {
  
	private View mMenuView;
	private ListView mListView;
	private TextView mDelete_allreturngoods;
	private List<ReturnNoteGoods> Show_Retrngoods_ProductListView;
    public ReturnGoodPopupWindow(final Activity context, final ReturnsProductPresenter Presenter) {
        super(context);  
        LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        mMenuView = inflater.inflate(R.layout.returngood_list_popupwindow, null);
        mListView=(ListView) mMenuView.findViewById(R.id.Show_Retrngoods_ProductListView);
		mDelete_allreturngoods=(TextView) mMenuView.findViewById(R.id.delete_allreturngoods);
		Show_Retrngoods_ProductListView=new ArrayList<ReturnNoteGoods>();
		Show_Retrngoods_ProductListView=Presenter.getReturnCart().getAllGoodsList();
        ShowReturnsGoodsListAdapter adapter=new ShowReturnsGoodsListAdapter(Show_Retrngoods_ProductListView, context);
        mListView.setAdapter(adapter);
        //设置SelectPicPopupWindow的View  
        this.setContentView(mMenuView);  
        //设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.MATCH_PARENT);  
        //设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.WRAP_CONTENT);  
        //设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        //设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.AnimBottom);  
        //实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0xb0000000);  
        //设置SelectPicPopupWindow弹出窗体的背景  
        this.setBackgroundDrawable(dw);  
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        
        //清空按钮的点击事件
		mDelete_allreturngoods.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialog.Builder builder = new CustomDialog.Builder(context);
		        builder.setMessage("您确认要删除这些商品吗？");
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
		            public void onClick(DialogInterface dialog, int which) {
						Presenter.removeAllGoods();
		            	dismiss();
		                dialog.dismiss();
		            }  
		        });  
		        builder.setNegativeButton("取消",  
			        new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			            }  
			    });  
		  
		        builder.create().show();
			}
		});
    }
}