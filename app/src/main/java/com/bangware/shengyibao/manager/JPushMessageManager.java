package com.bangware.shengyibao.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import com.bangware.shengyibao.config.Model;
import com.bangware.shengyibao.net.ThreadPoolUtils;
import com.bangware.shengyibao.thread.HttpPostThread;
import com.bangware.shengyibao.utils.AppContext;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 消息推送数据接口 
 * @author ccssll
 *
 */
public class JPushMessageManager {
	private Context context;
	
	public JPushMessageManager(Context context){
		this.context= context;
	}
	
	public void doMessage(){
		//获取保存下来的手机设配序列号
		String message_url = Model.MESSAGEURL+"?token="+ AppContext.getInstance().getUser().getLogin_token();
		SharedPreferences sharedPreferences = context.getSharedPreferences("RegistrationID", Activity.MODE_PRIVATE);
		String regId = sharedPreferences.getString("regId", "");
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("user_id", AppContext.getInstance().getUser().getUser_id());
			jsonData.put("identification", regId);
			jsonData.put("method", "update_mobile_identification");
			JSONObject messageData = new JSONObject();
			jsonData.put("data", messageData);
//			Toast.makeText(context, regId, Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ThreadPoolUtils.execute(new HttpPostThread(hand, message_url,"utf-8",jsonData));
	}
	
	Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 404) {
				Toast.makeText(context, "服务器地址错误", Toast.LENGTH_SHORT).show();
				
			} else if (msg.what == 100) {
				Toast.makeText(context, "网络传输失败", Toast.LENGTH_SHORT).show();
				
			} else if (msg.what == 200) {
				String result = (String) msg.obj;
//				Toast.makeText(context, "传输成功"+result, Toast.LENGTH_SHORT).show();
				
				if (result != null) {
//					Toast.makeText(context, "请求成功！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, "服务器连接失败！", Toast.LENGTH_SHORT).show();
				}
			}
		};
	};
}
