package com.bangware.shengyibao.activity;

import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class HtmlWebActivity extends BaseActivity {
    private WebView wvDetailBody;

    final Handler myHandler = new Handler();

    private String htmlBody;

    private String htmlPic;

    private ImageView htmlback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_web);
        wvDetailBody = (WebView) findViewById(R.id.detail_body);

        WebSettings webSettings = wvDetailBody.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        wvDetailBody.loadUrl("http://www.baidu.com");
        //设置Web视图
        wvDetailBody.setWebViewClient(new webViewClient ());
       /* htmlPic  = "http://www.baidu.com/img/baidu_sylogo1.gif";
        htmlBody = getString(R.string.test).toString();
        final JavaScriptInterface myJavaScriptInterface
                = new JavaScriptInterface(this);*/

//        wvDetailBody.getSettings().setLightTouchEnabled(true);
//        wvDetailBody.getSettings().setJavaScriptEnabled(true);
//        wvDetailBody.getSettings().setPluginState(PluginState.ON);
//        wvDetailBody.addJavascriptInterface(myJavaScriptInterface
//                , "MyContent");
//        wvDetailBody.setWebViewClient(new MyWebViewClient());
//        wvDetailBody.loadUrl("file:///android_asset/www/show_detail.html");*/

        htmlback = (ImageView) findViewById(R.id.html_back);
        htmlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvDetailBody.canGoBack()) {
            wvDetailBody.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();//结束退出程序
        return false;
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /*public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        public void showContacts() {
            // 封装成json传入　　ｊｓ中处理
            JSONObject jsonData = new JSONObject();
            final JSONArray jsonArry = new JSONArray();

            try {
                jsonData.put("pic", htmlPic);
                jsonData.put("content", htmlBody);
                jsonArry.put(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    // This gets executed on the UI thread so it can safely modify Views
                    wvDetailBody.loadUrl("javascript:show('" + jsonArry + "')");
                }
            });
        }
    }*/
}
