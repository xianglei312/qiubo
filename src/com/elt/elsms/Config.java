package com.elt.elsms;

import com.RTalk.R;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class Config {
	private static final String TAG = "Config";

	public static final String UPDATE_SERVER = "http://elw.so/ApkFiles/";
	public static final String UPDATE_APKNAME = "ELSMS.apk";
	public static final String UPDATE_VERJSON = "ver.json";
	public static final String UPDATE_SAVENAME = "updateELSMS.apk";

	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo("com.ELSMS", 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo("com.ELSMS", 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;

	}

	public static String getAppName(Context context) {
		String verName = context.getResources().getText(R.string.app_name).toString();
		return verName;
	}
}
