package com.bangware.shengyibao.updateversion;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.bangware.shengyibao.updateversion.model.entity.VersionBean;


/**
 * 数据解析
 * @author ccssll
 *
 */
public class UpdateJsonUtil {
	public static VersionBean getUpdateVersion(String key){
		VersionBean bean = null;
		try {
			JSONObject jsonObject = new JSONObject(key.toString()).getJSONObject("data");
			bean = new VersionBean();
			bean.setLink(jsonObject.getString("link"));
			bean.setPackageName(jsonObject.getString("packageName"));
			bean.setVersion(jsonObject.getInt("version"));
			bean.setMd5(jsonObject.getString("md5"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
}
