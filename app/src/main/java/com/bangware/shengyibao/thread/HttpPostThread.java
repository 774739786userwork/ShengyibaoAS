package com.bangware.shengyibao.thread;

import android.os.Handler;
import android.os.Message;


import com.bangware.shengyibao.net.MyPost;

import org.json.JSONObject;

import java.util.Map;


/**
 * 网络Post请求的线程
 *  
 * By：luming.tang
 * */

public class HttpPostThread implements Runnable {

	private Handler hand;
	private String url;
	private String encode;
	private Map<String, String> value = null;
	private JSONObject jsonValue=null;
	private MyPost myGet = new MyPost();
	
	
	public HttpPostThread(Handler hand, String url, String encode, Map<String, String> value){
		this.hand = hand;
		//拼接访问服务器完整的地址
		this.url = url;
		this.encode = encode;
		this.value = value;
	}
	public HttpPostThread(Handler hand, String url, String encode, JSONObject jsonValue){
		this.hand = hand;
		//拼接访问服务器完整的地址
		this.url = url;
		this.encode = encode;
		this.jsonValue = jsonValue;
	}

	@Override
	public void run() {
		//获取我们回调主ui的message
		Message msg = hand.obtainMessage();
		if(value!=null){
			String result = myGet.doPost(url, encode, value);
			msg.what = 200;
			msg.obj = result;
		}else if(jsonValue!=null){
			String result = myGet.doPost(url, encode, jsonValue);
			msg.what = 200;
			msg.obj = result;
		}else{
			msg.what=401;
			msg.obj = "无请求参数！";
		}
		
		//给主ui发送消息传递数据
		hand.sendMessage(msg);

	}

}
