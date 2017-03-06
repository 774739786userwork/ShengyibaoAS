package com.bangware.shengyibao.main.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bangware.shengyibao.activity.R;
import com.bangware.shengyibao.activity.SettingActivity;
import com.bangware.shengyibao.activity.Suggest.view.SuggestActivity;
import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.main.fragment.CustomerFragment;
import com.bangware.shengyibao.main.fragment.ReportFragment;
import com.bangware.shengyibao.main.fragment.SalerFragment;
import com.bangware.shengyibao.net.ThreadPoolUtils;
import com.bangware.shengyibao.thread.HttpGetThread;
import com.bangware.shengyibao.updateversion.VersionUpdateView;
import com.bangware.shengyibao.updateversion.model.entity.VersionBean;
import com.bangware.shengyibao.updateversion.presenter.UpdateVersionPresenter;
import com.bangware.shengyibao.updateversion.presenter.UpdateVersionPresenterImpl;
import com.bangware.shengyibao.updateversion.service.UpdateVersionService;
import com.bangware.shengyibao.user.model.entity.User;
import com.bangware.shengyibao.user.view.LoginActivity;
import com.bangware.shengyibao.utils.AppContext;
import com.bangware.shengyibao.utils.VersionUtil;
import com.bangware.shengyibao.utils.volley.DataRequest;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,VersionUpdateView {
    private long mExitTime = System.currentTimeMillis();
    private UpdateVersionPresenter versionPresenter;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    private VersionBean newVersionBean = new VersionBean();

    private BottomNavigationBar bottomNavigationBar;
    private int lastSelectedPosition = 0;
    private DrawerLayout drawer;

    private Fragment mContent;
    private SalerFragment salerFragment;
    private CustomerFragment customerFragment;
    private ReportFragment reportFragment;

    private User user;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!=null)
        {
            DataRequest.buildRequestQueue(this);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * onSaveInstanceState和onRestoreInstanceState保存状态与恢复
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e("HELLO", "HELLO：当Activity被销毁的时候，不是用户主动按back销毁，例如按了home键");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("HELLO", "HELLO:如果应用进程被系统咔嚓，则再次打开应用的时候会进入");
    }

    private void init(){
        mContent=new FragmentSaler();
        //FIXME modify by mouse
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if(fm.findFragmentById(R.id.content_frame) != null){
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            transaction.replace(R.id.content_frame,mContent);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        // 侧边栏
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /**底部tab栏**/
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setBarBackgroundColor(R.color.white);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//点击时的动画效果
        bottomNavigationBar.setInActiveColor(R.color.gray);//未选中时的颜色
        bottomNavigationBar.setActiveColor(R.color.colorPrimaryDark);//选中时的颜色

        //添加底部button控件
        bottomNavigationBar                       //选中图片                                         //未选中图片
                .addItem(new BottomNavigationItem(R.drawable.ic_navigation_tab_home_press, "首页").setInactiveIcon(ContextCompat.getDrawable(this,R.drawable.ic_navigation_tab_home_default)))
                .addItem(new BottomNavigationItem(R.drawable.ic_navigation_tab_fund_press, "销售管理").setInactiveIcon(ContextCompat.getDrawable(this,R.drawable.ic_navigation_tab_fund_default)))
                .addItem(new BottomNavigationItem(R.drawable.ic_navigation_tab_product_press, "客户管理").setInactiveIcon(ContextCompat.getDrawable(this,R.drawable.ic_navigation_tab_product_default)))
                .addItem(new BottomNavigationItem(R.drawable.ic_navigation_tab_report_press, "报表").setInactiveIcon(ContextCompat.getDrawable(this,R.drawable.ic_navigation_tab_report_default)))
                .setFirstSelectedPosition(lastSelectedPosition).initialise();

        MyTabSelect myTabSelect = new MyTabSelect();
        bottomNavigationBar.setTabSelectedListener(myTabSelect);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //点击图标弹出侧边栏
        ImageView imageView = (ImageView) findViewById(R.id.title_bar_menu_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        sharedPreferences = this.getSharedPreferences(User.SHARED_NAME,MODE_PRIVATE);
        user = AppContext.getInstance().readFromSharedPreferences(sharedPreferences);

        //版本更新调用
        versionPresenter = new UpdateVersionPresenterImpl(this);
//        versionPresenter.versionUpdate(user);
    }


    @Override
    protected void onDestroy() {
        if (versionPresenter != null){
            versionPresenter.destroy();
        }
        super.onDestroy();
    }

    private class  MyTabSelect implements BottomNavigationBar.OnTabSelectedListener{

        @Override
        public void onTabSelected(int position) {
            FragmentManager fm = MainActivity.this.getSupportFragmentManager();
            //开启事务
            FragmentTransaction transaction = fm.beginTransaction();
            switch (position) {
                case 0:
                    if (mContent == null) {
                        mContent = new FragmentSaler();
                    }
                    transaction.replace(R.id.content_frame, mContent);
                    break;
                case 1:
                    if (salerFragment == null) {
                        salerFragment = new SalerFragment();
                    }
                    transaction.replace(R.id.content_frame, salerFragment);
                    break;
                case 2:
                    if (customerFragment == null) {
                        customerFragment = new CustomerFragment();
                    }
                    transaction.replace(R.id.content_frame, customerFragment);
                    break;
                case 3:
                    if (reportFragment == null){
                        reportFragment = new ReportFragment();
                    }
                    transaction.replace(R.id.content_frame,reportFragment);
                    break;
            }
            transaction.commit();//提交事务
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if (id == R.id.toolbox_title) {
            drawer.closeDrawer(GravityCompat.START);
            // Handle the camera action
        }else if (id == R.id.toolbar_setting) {
            intent=new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.toolbar_suggest) {
            intent=new Intent(this, SuggestActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.toolbar_logout) {
            String url = Model.HTTPURL+"users/sign_out.json?token="+ user.getLogin_token();
            clearUserCache();
            Intent loginIntent = new Intent(this,LoginActivity.class);
            startActivity(loginIntent);
            this.finish();
            ThreadPoolUtils.execute(new HttpGetThread(handler, url));
       }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if(msg.what == 404){
            }
            if(msg.what == 100){
            }
            if(msg.what == 200){
                String result = (String) msg.obj;
                if (result != null) {
                }
            }
        };
    };
    /**
     * 清除本地用户信息缓存
     */
    private void clearUserCache(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(User.SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("username", user.getUser_name());
        editor.commit();
    }


    @Override
    public void doUpdateVersionSuccess(VersionBean versionBean) {
        this.newVersionBean = versionBean;
        if(VersionUtil.getVersionCode(MainActivity.this) < newVersionBean.getVersion()){
            if(Build.VERSION.SDK_INT >= 23) {
                int checkSMSPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(checkSMSPermission != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    return;
                }else{
                    showVersionUpdateConfirmDialog(newVersionBean.getLink());
                }
            }else{
                showVersionUpdateConfirmDialog(newVersionBean.getLink());
            }
        }else{
//            Toast.makeText(MainActivity.this, "当前是最新版本", Toast.LENGTH_SHORT).show();
        }
    }
    //确认更新dialog
    private void showVersionUpdateConfirmDialog(final String link)
    {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("版本有更新，您是否需要现在更新？");
        builer.setCancelable(false);
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //拿到从后台解析的apk文件链接地址并传至服务类进行下载更新
                Intent intent = new Intent(MainActivity.this, UpdateVersionService.class);
                intent.putExtra("link",link);
                startService(intent);
                Toast.makeText(MainActivity.this, "正在更新", Toast.LENGTH_SHORT).show();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builer.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    showVersionUpdateConfirmDialog(newVersionBean.getLink());
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this,"用户取消了权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }else if ((System.currentTimeMillis() - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                System.exit(0);// 否则退出程序
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
