package com.bangware.shengyibao.utils.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.bangware.shengyibao.activity.R;

/**
 * CustomDialog
 * 自定义dialog，使用传入宽高，xml即可，跟使用系统dialog相似
 * 只是这个dialog可以使用自定义的xml
 */
public class CommonDialog extends Dialog {
    private static int default_width = 160; //默认宽度
    public CommonDialog(Context context) {
        super(context);
    }

    public CommonDialog(Context context, int layout, int style) {
        this(context, default_width, layout, style);
    }

    public CommonDialog(Context context, int width, int layout, int style) {
        super(context, style);
        //set content
        setContentView(layout);
        //set window params
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        //set width,height by density and gravity
        float density = getDensity(context);
        params.width = width;
//        params.height = (int) (height*density);
//        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    @Override
    public void show() {
        if (!isShowing()){
            super.show();
        }
    }
}
