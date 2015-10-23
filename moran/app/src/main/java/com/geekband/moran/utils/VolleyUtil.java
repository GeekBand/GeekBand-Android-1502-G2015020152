package com.geekband.moran.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyUtil {

	private volatile static RequestQueue requestQueue;

	/** 返回RequestQueue单例 **/
	public static RequestQueue getQueue(Context context) {
		if (requestQueue == null) {
			synchronized (VolleyUtil.class) {
				if (requestQueue == null) {
					requestQueue = Volley.newRequestQueue(context.getApplicationContext());
				}
			}
		}
		return requestQueue;
	}

	public  static void myCancleAll(Context context){
		//请求之前，先取消之前的请求（取消还没有进行完的请求）
		VolleyUtil.getQueue(context).cancelAll(context);
	}

	public static void addRequest(Context context , Request request){
		VolleyUtil.getQueue(context).add(request);
	}

}
