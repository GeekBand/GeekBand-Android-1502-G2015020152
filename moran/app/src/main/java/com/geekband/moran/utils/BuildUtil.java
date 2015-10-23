package com.geekband.moran.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;


/**
 * 安卓系统及应用程序相关类. <br/>
 * 在使用该方法之前, 需要先调用 {@link #init(Context)}初始化一下. <br/>
 * <br/>
 * 如果应用程序的Application是继承自 BaseApplication}, 则不需要再进行初始化了, 可以直接使用
 * 
 * @author wangjingtao
 * 
 */
public class BuildUtil {

	private static Context context;

	public static void init(Context ctx) {
		context = ctx;
	}

	/**
	 * 获取当前应用程序的版本号(获取AndroidManifest文件中android:versionName属性值)
	 * 
	 * @return 当前应用程序的版本号
	 */
	public static String getAppVersionName() {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			// 应用程序版本名称, 例如2.1.1
			String appVersionName = info.versionName;
			return appVersionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return "0.0";
	}

	/**
	 * 获取当前应用程序的版本号(获取AndroidManifest文件中android:versionCode属性值)
	 * 
	 * @return 当前应用程序的版本号
	 */
	public static int getAppVersionCode() {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);

			// 应用程序的版本号
			int appVersionCode = info.versionCode;

			return appVersionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return 1;
	}

	/**
	 * 获取手机的IMEI
	 * 
	 * @return 手机的IMEI
	 */
	public static String getIMEI() {

		return DeviceId.getIMEI(context);
	}

	/**
	 * 获取当前手机的DeviceId, 唯一标识一台设备
	 * 
	 * @return 当前手机的DeviceId
	 */
	public static String getDeviceID() {
		return DeviceId.getDeviceID(context);
	}

	/**
	 * 获取当前用户的cuid， 用于统计
	 * 
	 * @return
	 */
	public static String getCuid() {
		String deviceId = getDeviceID();
		String imei = getIMEI();
		if (TextUtils.isEmpty(imei)) {
			imei = "0";
		}
		StringBuilder builder = new StringBuilder(imei);
		return deviceId + "|" + builder.reverse().toString();

	}

	/**
	 * 获取手机的型号信息, 例如 Xiaomi_2S
	 * 
	 * @return 手机的型号信息
	 */
	public static String getModelInfo() {
		return Build.MANUFACTURER + "_" + Build.MODEL;
	}

	/**
	 * 获取手机操作系统的版本号, 例如4.0.3
	 * 
	 * @return 手机操作系统的版本号
	 */
	public static String getOsVersionName() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取百度移动统计的渠道号
	 * 
	 * @return
	 */
	public static String getMtjChannel() {
		try {
			ApplicationInfo appInfo = context.getApplicationContext().getPackageManager()
					.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return appInfo.metaData.getString("BaiduMobAd_CHANNEL");
		} catch (Exception e) {

		}

		return "";
	}

	/**
	 * 模拟系统Home键按下效果方式退出app
	 * 
	 * @param context
	 */
	public static void exitApp(Context context) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(intent);
	}
}
