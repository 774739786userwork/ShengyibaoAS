package com.bangware.shengyibao.utils.volley;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.bangware.shengyibao.utils.MD5Util;

import java.io.File;
import java.io.FileOutputStream;

public class BitmapCache implements ImageCache {
	/** 内存缓存 */
	private static final int MEMORY_CACHE_MAX_SIZE = 4 * 1024 * 1024;
	private static LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(MEMORY_CACHE_MAX_SIZE) {
		@Override
		protected int sizeOf(String key, Bitmap bitmap) {
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
	};
	/** 本地缓存目录 */
	private String localCacheDir;
	private boolean bCache2File = false;
	public BitmapCache() {}
	/**
	 * 设置本地磁盘目录
	 * @param cachedir
	 */
	public void setLocalCacheDir(File cachedir){
		Log.d("BitmapCache", "设置本地缓存目录:"+cachedir);
		this.bCache2File = true;
		this.localCacheDir = cachedir + File.separator;
	}
	

	@Override
	public Bitmap getBitmap(String url) {
		// 获取图片
		try {
			String key = MD5Util.toMd5HexString(url);
			Bitmap image = mMemoryCache.get(key);
			if (image != null) {
				return image;
			} else {
				File file = new File(localCacheDir + key);
				if (file.exists()) {
					Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
					return bitmap;
				}
			}
		} catch (Exception e) {
			Log.d("getBitmap", e.getMessage());
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		if(bitmap!=null){
			String key = MD5Util.toMd5HexString(url);
			// 放入图片到内存
			mMemoryCache.put(key, bitmap);
			// 将图片放入到磁盘
			if(bCache2File){
				FileOutputStream os = null;
				try {
					Log.d("BitmapCache", "图片缓存到"+localCacheDir);
					File file = new File(localCacheDir + key);
					os = new FileOutputStream(file, false);
					bitmap.compress(CompressFormat.PNG, 100, os);
					os.flush();
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
