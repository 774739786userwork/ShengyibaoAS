package com.bangware.shengyibao.utils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Lian on 2016/5/7.
 */
public class DownloadUtils
{
    /**
     * 要下载的大文件的地址
     */
    private String fileUrl;
    /**
     * 要将下载的文件放的位置
     */
    private File targetFile;
    /**
     * 回调接口
     */
    private OnDownloadListener onDownloadListener;

    /**
     * 线程数量
     */
    private int threadCount;

    /**
     * 当前下载的文件的总大小
     */
    private int currDownloadLen;

    public DownloadUtils(String fileUrl, File targetFile)
    {
        this(3,fileUrl,targetFile,null);
    }

    public DownloadUtils(String fileUrl, File targetFile, OnDownloadListener onDownloadListener)
    {
        this(3,fileUrl,targetFile,onDownloadListener);
    }

    public DownloadUtils(int threadCount, String fileUrl, File targetFile, OnDownloadListener onDownloadListener)
    {
        this.threadCount = threadCount;
        this.fileUrl = fileUrl;
        this.targetFile = targetFile;
        this.onDownloadListener = onDownloadListener;
    }

    /**
     * 下载的回调接口
     */
    public interface OnDownloadListener
    {
        /**
         * 返回当前下载的进度   会转换成总进度是100返回
         * @param progress
         */
        public abstract void onProgressChange(int progress);
        void onBegin();
        void onEnd();
    }

    /**
     * 开始下载
     */
    public void download()
    {
        //将当前的下载进度归0
        this.currDownloadLen = 0;

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //1.得到要下载的文件的大小
                    URL url = new URL(fileUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    int total = 0;
                    if (connection.getResponseCode() == 200)
                    {
                        total = connection.getContentLength();
                        connection.disconnect();
                    }

                    //2.创建同样大小的文件供写
                    RandomAccessFile rFile = new RandomAccessFile(targetFile, "rw");
                    rFile.setLength(total);
                    rFile.close();

                    //回调开始下载
                    if(onDownloadListener!=null)
                    {
                        onDownloadListener.onBegin();
                    }

                    //3.计算每个线程下载的大小
                    int threadLen = total % threadCount == 0 ? total / threadCount : total
                            / threadCount + 1;
                    for (int i = 0; i < threadCount; i++)
                    {
                        new Thread(new DownloadRunnable(i,threadLen,total)).start();
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                    //throw new IllegalStateException("系统错误，错误信息："+e.getMessage());
                }
            }
        }).start();
    }

    class DownloadRunnable implements Runnable
    {
        int total;
        int start;
        int end;
        /*
         * 参一：当前是第几个线程 参二：每个线程下载多大 参三：文件的总大小
         */
        public DownloadRunnable(int i, int threadLen,int total)
        {
            //计算每个线程下载的开始位置和结束位置
            start = i * threadLen;
            end = (i + 1) * threadLen-1;
            this.total = total;
        }

        @Override
        public void run()
        {
            try
            {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection)url
                        .openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                // 设置当前这个线程的下载的位置
                connection.setRequestProperty("Range", "bytes="+start+"-"+end);

                //开始下载
                RandomAccessFile rFile = new RandomAccessFile(targetFile, "rw");
                //设置当前线程文件的开始写入的位置
                rFile.seek(start);

                //得到输入流
                InputStream is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buf = new byte[1024*20];
                int len = -1;
                while((len = bis.read(buf))!=-1)
                {
                    rFile.write(buf, 0, len);

                    //回调下载的进度更新
                    if(onDownloadListener!=null)
                    {
                        currDownloadLen+=len;
                        int progress = (int) ((currDownloadLen+0.0)/total*100);
                        onDownloadListener.onProgressChange(progress);
                    }
                }

                //关闭
                is.close();
                bis.close();
                rFile.close();

                //下载完毕
                synchronized (this)
                {
                    if(onDownloadListener!=null && currDownloadLen==total)
                    {
                        currDownloadLen++;//将currDownloadLen增加1，防止重复的回调
                        onDownloadListener.onEnd();
                    }
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (ProtocolException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
