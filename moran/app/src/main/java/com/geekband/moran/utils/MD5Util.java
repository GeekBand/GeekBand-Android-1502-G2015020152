package com.geekband.moran.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class MD5Util {

	public static String toMd5(byte[] paramArrayOfByte, boolean toUpperCase) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.reset();
			localMessageDigest.update(paramArrayOfByte);
			return toHexString(localMessageDigest.digest(), "", toUpperCase);
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			throw new RuntimeException(localNoSuchAlgorithmException);
		}
	}

	public static String toHexString(byte[] paramArrayOfByte, String paramString, boolean toUpperCase) {
		StringBuilder localStringBuilder = new StringBuilder();
		for (int k : paramArrayOfByte) {
			String str = Integer.toHexString(0xFF & k);
			if (toUpperCase)
				str = str.toUpperCase(Locale.US);
			if (str.length() == 1)
				localStringBuilder.append("0");
			localStringBuilder.append(str).append(paramString);
		}
		return localStringBuilder.toString();
	}
}
