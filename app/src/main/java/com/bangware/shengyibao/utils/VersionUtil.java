package com.bangware.shengyibao.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VersionUtil {
	private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm:ss");
    //得到当前的时间
    public static String getCurrTime()
    {
        return SDF_TIME.format(new Date());
    }

    /**
     * 获取当前的版本号
     *
     * @param context
     *            上下文对象
     * @return 当前版本
     */
    public static int getVersionCode(Context context)// 获取版本号(内部识别号)
    {
        try
        {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
