package com.bangware.shengyibao.customer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

/**
 * 网络请求
 * @author ccssll
 *
 */
public class HttpUtils {
	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}
	 public static String getJsonContent(String url_path ,String encode){
         
	        String jsonString = "";
	        try {
	            URL url = new URL(url_path);  
	            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	            connection.setConnectTimeout(3000);
	            connection.setRequestMethod("GET");
	            connection.setDoInput(true);  //从服务器获得数据
	             
	            int responseCode = connection.getResponseCode();            
	             
	            Log.d("TAG", ""+responseCode);
	            if (200 == responseCode) {
	            	
	                jsonString = changeInputStream(connection.getInputStream(),encode);
	                 
	            }
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	        return jsonString;
	    }
	 
	    private static String changeInputStream(InputStream inputStream , String encode) throws IOException {
	        // TODO Auto-generated method stub
	        String  jsonString = null;
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        byte[] data = new byte[1024];
	        int len = 0;
	        while((len=inputStream.read(data))!=-1){
	            outputStream.write(data, 0, len);
	        }
	        jsonString = new String(outputStream.toByteArray(), encode);
	        inputStream.close();
	        return jsonString;
	    }
}
