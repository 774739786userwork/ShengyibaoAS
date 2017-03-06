package com.bangware.shengyibao.utils.volley;


import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

/**
 * 图片请求处理
 * @author luming.tang
 *
 */
public class ImageRequester {
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
	private void init(Context context, boolean bCache2File){
		mRequestQueue = Volley.newRequestQueue(context);
		BitmapCache bitmapCache = new BitmapCache();
		if(bCache2File){
			bitmapCache.setLocalCacheDir(context.getCacheDir());
		}
    	mImageLoader = new ImageLoader(mRequestQueue,bitmapCache);
	}
	public ImageRequester(Context context, boolean bCache2File){
		init(context, bCache2File);
	}
	public ImageRequester(Context context){
		init(context,false);
	}
	
	public ImageLoader getImageLoader(){
		return mImageLoader;
	}
	/**
	 * 取消所有请求
	 */
	public void cancelAllRequest(){
    	mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
    }
	/**
	 * 加载网络图片
	 * @param imageView 图片显示对象
	 * @param url 图片路径
	 * @param defaultImageResId 默认图片ID
	 * @param errorImageResId 错误图片ID
	 */
   public void load(ImageView imageView, String url, int defaultImageResId, int errorImageResId){
	   ImageListener listener = ImageLoader.getImageListener(imageView, defaultImageResId,errorImageResId);
	   mImageLoader.get(url, listener);
   }
}
