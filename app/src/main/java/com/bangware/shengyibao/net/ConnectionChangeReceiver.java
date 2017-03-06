package com.bangware.shengyibao.net;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.utils.customdialog.CommonDialog;

/**
 * 广播注册网络判断
 * Created by ccssll on 2016/8/15.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver{
    private CommonDialog customDialog;
    @Override
    public void onReceive(final Context context, Intent intent) {
        try{
            ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo  mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo  info = connectivityManager.getActiveNetworkInfo();
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                int screenView = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
                customDialog = new CommonDialog(context,screenView, R.layout.common_dialog_layout,R.style.custom_dialog);
                TextView tv_dialog_login_context = (TextView)customDialog.findViewById(R.id.tv_dialog_common_context);
                TextView tv_dialog_login_go = (TextView)customDialog.findViewById(R.id.tv_dialog_common_go);
                TextView tv_dialog_login_close = (TextView)customDialog.findViewById(R.id.tv_dialog_common_close);
                tv_dialog_login_context.setText("当前网络不可用，请检查网络设置！");
                tv_dialog_login_go.setText("去设置");
                customDialog.setCanceledOnTouchOutside(false);
                tv_dialog_login_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (android.os.Build.VERSION.SDK_INT > 10) {
                            context.startActivity(new Intent(
                                    android.provider.Settings.ACTION_SETTINGS));
                        } else {
                            context.startActivity(new Intent(
                                    android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                        customDialog.dismiss();
                    }
                });
                tv_dialog_login_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = info.getSubtype();
                if(subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS || subType == TelephonyManager.NETWORK_TYPE_EDGE){
                    Toast.makeText(context,"当前为2g网络,网络状况不佳!",Toast.LENGTH_LONG).show();
                    customDialog.dismiss();
                    return;
                }else if(subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_B){
//                    Toast.makeText(context,"当前为3g网络!",Toast.LENGTH_LONG).show();
                    customDialog.dismiss();
                    return;
                }else if (subType == TelephonyManager.NETWORK_TYPE_LTE){
//                    Toast.makeText(context,"当前为4g网络!",Toast.LENGTH_LONG).show();
                    customDialog.dismiss();
                    return;
                }
            }else {
                if (customDialog != null){
                    customDialog.dismiss();
                    return;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}