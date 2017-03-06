package com.bangware.shengyibao.utils.customdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * 下拉选择框dialog
 * @author ccssll
 *
 */
public class SelectDialog extends AlertDialog {
	public SelectDialog(Context context, int theme) {
		super(context, theme);
	}

	public SelectDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.slt_cnt_type);
	}
}
