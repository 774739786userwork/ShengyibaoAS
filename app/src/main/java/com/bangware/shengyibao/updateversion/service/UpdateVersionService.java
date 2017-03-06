package com.bangware.shengyibao.updateversion.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.utils.DownloadUtils;

import java.io.File;

/**
 * 版本更新服务类
 */
public class UpdateVersionService extends Service
{
    NotificationManager mNotMgr;
    NotificationCompat.Builder mBuilder;
    File mTargetFile;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("onStartCommand","进入服务--------------------------");
        mNotMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("邦微ShengYiBao.apk正在更新......");

        //下载的地址
        try{
            String link = intent.getStringExtra("link");
            donwload(link);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"下载失败，请重新下载",Toast.LENGTH_SHORT);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    //开始下载
    private void donwload(String link)
    {
        mTargetFile = new File(this.getExternalCacheDir(),"ShengYiBao.apk");
        DownloadUtils downloadUtils = new DownloadUtils(link, mTargetFile, new DownloadUtils.OnDownloadListener()
        {
            @Override
            public void onProgressChange(int progress)
            {

                    mBuilder.setProgress(100, progress, false);
                    mBuilder.setContentText(progress + "%");
                    mNotMgr.notify(1000, mBuilder.build());
            }

            @Override
            public void onBegin()
            {
            }

            @Override
            public void onEnd()
            {
                //如果已经下载成功，就开始安装
                installApk();
            }
        });
        downloadUtils.download();
        
    }

    private void installApk()
    {
        if (!mTargetFile.exists())
        {
            Log.i("DownLoadReceive", "文件不存在");
            Toast.makeText(UpdateVersionService.this,"下载失败，请重新下载",Toast.LENGTH_SHORT);
            return;
        }
        // 通过Intent安装apk文件，自动打开安装界面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(mTargetFile),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 由于是在BroadcastReceive中启动activity，所以启动方式必须设置为FLAG_ACTIVITY_NEW_TASK
        this.startActivity(intent);

        //停止服务
        stopSelf();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("onDestroy","结束服务--------------------------");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
