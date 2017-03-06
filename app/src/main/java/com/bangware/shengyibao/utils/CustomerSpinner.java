package com.bangware.shengyibao.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.customer.adapter.CustomerSalerAreaAdapter;
import com.bangware.shengyibao.customer.model.entity.CustomerSalerArea;
import com.bangware.shengyibao.utils.customdialog.SelectDialog;


import java.util.List;

public class CustomerSpinner extends Spinner implements OnItemClickListener {
	public static SelectDialog dialog = null;
	private List<CustomerSalerArea> salerAreaslist;
	public static String text;

	public CustomerSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	//如果视图定义了OnClickListener监听器，调用此方法来执行
	@Override
	public boolean performClick() {
		Context context = getContext();
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		final View view = inflater.inflate(R.layout.formcustomspinner, null);
		final ListView listview = (ListView) view
				.findViewById(R.id.formcustomspinner_list);
		CustomerSalerAreaAdapter adapters = new CustomerSalerAreaAdapter(context, getList());
		listview.setAdapter(adapters);
		listview.setOnItemClickListener(this);
		dialog = new SelectDialog(context, R.style.spinner_dialog);//创建Dialog并设置样式主题
		LayoutParams params = new LayoutParams(650, LayoutParams.FILL_PARENT);
		dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		dialog.show();
		dialog.addContentView(view, params);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> view, View itemView, int position,
							long id) {
		// TODO Auto-generated method stub
		setSelection(position);
		setText(salerAreaslist.get(position).toString());
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	
	public List<CustomerSalerArea> getList() {
		return salerAreaslist;
	}

	public void setList(List<CustomerSalerArea> salerAreaslist) {
		this.salerAreaslist = salerAreaslist;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
