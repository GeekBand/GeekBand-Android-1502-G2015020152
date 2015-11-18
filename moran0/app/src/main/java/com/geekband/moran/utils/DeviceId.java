package com.geekband.moran.utils;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public final class DeviceId {

	private static final String KEY_DEVICE_ID = "com.baidu.deviceid";
	private static final String AES_KEY = "30212102dicudiab";
	private static final String EXT_FILE = "baidu/.cuid";

	public static String getDeviceID(Context context) {
		checkPermission(context, "android.permission.WRITE_SETTINGS");
		checkPermission(context, "android.permission.READ_PHONE_STATE");
		checkPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
		IMEIInfo localIMEIInfo = IMEIInfo.getIMEIInfo(context);
		String imei = localIMEIInfo.IMEI;
		int i = !localIMEIInfo.CAN_READ_AND_WRITE_SYSTEM_SETTINGS ? 1 : 0;
		String androidId = getAndroidId(context);
		String deviceId = "";
		if (i != 0)
			return MD5Util.toMd5(new StringBuilder().append("com.baidu").append(androidId).toString().getBytes(), true);
		
		String bdDeviceId = null;
		deviceId = Settings.System.getString(context.getContentResolver(), KEY_DEVICE_ID);
		
		if (TextUtils.isEmpty(deviceId)) {
			bdDeviceId = MD5Util.toMd5(new StringBuilder().append("com.baidu").append(imei).append(androidId).toString()
					.getBytes(), true);
			deviceId = Settings.System.getString(context.getContentResolver(), bdDeviceId);
			if (!TextUtils.isEmpty(deviceId)) {
				Settings.System.putString(context.getContentResolver(), KEY_DEVICE_ID, deviceId);
				setExternalDeviceId(imei, deviceId);
			}
		}
		if (TextUtils.isEmpty(deviceId)) {
			deviceId = getExternalDeviceId(imei);
			if (!TextUtils.isEmpty(deviceId)) {
				Settings.System.putString(context.getContentResolver(), bdDeviceId, deviceId);
				Settings.System.putString(context.getContentResolver(), KEY_DEVICE_ID, deviceId);
			}
		}
		if (TextUtils.isEmpty(deviceId)) {
			String str5 = UUID.randomUUID().toString();
			deviceId = MD5Util
					.toMd5(new StringBuilder().append(imei).append(androidId).append(str5).toString().getBytes(), true);
			Settings.System.putString(context.getContentResolver(), bdDeviceId, deviceId);
			Settings.System.putString(context.getContentResolver(), KEY_DEVICE_ID, deviceId);
			setExternalDeviceId(imei, deviceId);
		}
		return deviceId;
	}

	public static String getIMEI(Context context) {
		IMEIInfo localIMEIInfo = IMEIInfo.getIMEIInfo(context);
		return localIMEIInfo.IMEI;
	}

	public static String getAndroidId(Context context) {
		String str = "";
		str = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		if (TextUtils.isEmpty(str))
			str = "";
		return str;
	}

	private static void checkPermission(Context context, String paramString) {
		int i = context.checkCallingOrSelfPermission(paramString);
		int j = i == 0 ? 1 : 0;
		if (j == 0)
			throw new SecurityException(new StringBuilder().append("Permission Denial: requires permission ")
					.append(paramString).toString());
	}

	private static String getExternalDeviceId(String paramString) {
		if (TextUtils.isEmpty(paramString))
			return "";
		String str1 = "";
		File localFile = new File(Environment.getExternalStorageDirectory(), EXT_FILE);
		try {
			FileReader localFileReader = new FileReader(localFile);
			BufferedReader localBufferedReader = new BufferedReader(localFileReader);
			StringBuilder localStringBuilder = new StringBuilder();
			String str2 = null;
			while ((str2 = localBufferedReader.readLine()) != null) {
				localStringBuilder.append(str2);
				localStringBuilder.append("\r\n");
			}
			localBufferedReader.close();
			String str3 = new String(AESUtil.decrypt(AES_KEY, AES_KEY,
					Base64.decode(localStringBuilder.toString().getBytes())));
			String[] arrayOfString = str3.split("=");
			if ((arrayOfString != null) && (arrayOfString.length == 2) && (paramString.equals(arrayOfString[0])))
				str1 = arrayOfString[1];
		} catch (FileNotFoundException localFileNotFoundException) {
		} catch (IOException localIOException) {
		} catch (Exception localException) {
		}
		return str1;
	}

	private static void setExternalDeviceId(String imei, String deviceId) {
		if (TextUtils.isEmpty(imei))
			return;
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append(imei);
		localStringBuilder.append("=");
		localStringBuilder.append(deviceId);
		File localFile = new File(Environment.getExternalStorageDirectory(), EXT_FILE);
		try {
			new File(localFile.getParent()).mkdirs();
			FileWriter localFileWriter = new FileWriter(localFile, false);
			String str = Base64.encode(AESUtil.encrypt(AES_KEY, AES_KEY, localStringBuilder.toString().getBytes()),
					"utf-8");
			localFileWriter.write(str);
			localFileWriter.flush();
			localFileWriter.close();
		} catch (IOException localIOException) {
		} catch (Exception localException) {
		}
	}

	static final class IMEIInfo {
		private static final String KEY_IMEI = "bd_setting_i";
		public final String IMEI;
		public final boolean CAN_READ_AND_WRITE_SYSTEM_SETTINGS;
		public static final String DEFAULT_TM_DEVICEID = "";

		private IMEIInfo(String paramString, boolean paramBoolean) {
			this.IMEI = paramString;
			this.CAN_READ_AND_WRITE_SYSTEM_SETTINGS = paramBoolean;
		}

		private static String getIMEI(Context context, String paramString) {
			String str = null;
			try {
				TelephonyManager localTelephonyManager = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (localTelephonyManager != null)
					str = localTelephonyManager.getDeviceId();
			} catch (Exception e) {
				// Log.e("DeviceId", "Read IMEI failed", localException);
			}
			str = imeiCheck(str);
			if (TextUtils.isEmpty(str))
				str = paramString;
			return str;
		}

		static IMEIInfo getIMEIInfo(Context context) {
			int i = 0;
			String imei = "";
			try {
				imei = Settings.System.getString(context.getContentResolver(), KEY_IMEI);
				if (TextUtils.isEmpty(imei))
					imei = getIMEI(context, "");
				Settings.System.putString(context.getContentResolver(), KEY_IMEI, imei);
			} catch (Exception localException) {
				// Log.e("DeviceId",
				// "Settings.System.getString or putString failed",
				// localException);
				i = 1;
				if (TextUtils.isEmpty(imei))
					imei = getIMEI(context, "");
			}
			return new IMEIInfo(imei, i == 0);
		}

		private static String imeiCheck(String imei) {
			if ((null != imei) && (imei.contains(":")))
				return "";
			return imei;
		}
	}
}