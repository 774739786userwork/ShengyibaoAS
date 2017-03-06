package com.bangware.shengyibao.utils;
import android.content.SharedPreferences;

import com.bangware.shengyibao.user.model.entity.User;

/**
 * 系统全局环境
 * @author luming.tang
 *
 */
public class AppContext {
	private AppContext(){}
	private static AppContext APP_CONTEXT = new AppContext();
	public static AppContext getInstance(){
		return APP_CONTEXT;
	}
	
	private User user = null;
	public void setUser(User user){
		this.user = user;
	}
	public User getUser(){
		return user;
	}

	/** 判断是否是快速点击 */
	private static long lastClickTime;

	/** 防止多次快速点击打开多个activity的问题 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 从缓存中获取用户信息
	 * @return
	 */
	public User readFromSharedPreferences(SharedPreferences sharedPreferences){
		User user = new User();
		user.setUser_name(sharedPreferences.getString("username", ""));
		user.setPassword(sharedPreferences.getString("password", ""));
		user.setLogin_token(sharedPreferences.getString("login_token", ""));
		user.setUser_realname(sharedPreferences.getString("user_real_name", ""));
		user.setOrg_id(sharedPreferences.getString("organization_id", ""));
		user.setOrg_name(sharedPreferences.getString("organization_name", ""));
		user.setRoles(sharedPreferences.getString("roles", ""));
		user.setEmployee_id(sharedPreferences.getString("employee_id", ""));
		user.setUser_id(sharedPreferences.getString("user_id", ""));
		return user;
	}
}
