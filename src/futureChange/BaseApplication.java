package futureChange;

import org.yuner.www.bean.UserInfo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @author Jason Xiang
 */
public class BaseApplication extends Application {
	/** 网络标示 **/
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	/** 全局Context */
	private static BaseApplication mInstance;

	@Override
	public void onCreate() {
		Log.d("rtalk", "application启动了");
		mInstance = this;

		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		int heapSize = manager.getMemoryClass();
		Log.d("内存堆栈大小:", heapSize + "");
	}

	public static BaseApplication getApplication() {
		return mInstance;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public int getNetworkType() {
		int type = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return type;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (extraInfo.equals("")) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					type = NETTYPE_CMNET;
				} else {
					type = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			type = NETTYPE_WIFI;
		}
		return type;
	}

	/**
	 * 判断是否显示隐藏欢迎界面
	 * 
	 */
	public boolean ishideWelcomePage() {
		return getConfigInfoBoolean(ContentValue.HIDE_WELCOME_PAGE);
	}

	/**
	 * 判断用户是否登录
	 */
	public boolean isLogin() {
		return BaseApplication.getApplication().getConfigInfoBoolean(ContentValue.IS_LOGIN);
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 */
	public void saveUserInfo(UserInfo user) {
		setConfingInfo(ContentValue.IS_LOGIN, true);
		setConfingInfo(ContentValue.USER_AGE, user.getAge());
		setConfingInfo(ContentValue.USER_AVATAR_ID, user.getAvatarId());
		setConfingInfo(ContentValue.USER_BIRTH_DAY, user.getBirthDay());
		setConfingInfo(ContentValue.USER_BIRTH_MONTH, user.getBirthMonth());
		setConfingInfo(ContentValue.USER_BIRTH_YEAR, user.getBirthYear());
		setConfingInfo(ContentValue.USER_HOMETOWN, user.getHometown());
		setConfingInfo(ContentValue.USER_ID, user.getId());
		setConfingInfo(ContentValue.USER_LOCATION, user.getCurLocation());
		setConfingInfo(ContentValue.USER_NAME, user.getName());
		setConfingInfo(ContentValue.USER_ONLINE, user.getIsOnline());
		setConfingInfo(ContentValue.USER_SEX, user.getSex());
		setConfingInfo(ContentValue.USER_SINGUP_TIME, user.getSignupTime());
	}

	// /**
	// * 清空用户信息
	// *
	// */
	// public void clearUserInfo() {
	// this.userid = "";
	// setConfingInfo(ContentValue.USER_CONFIG_PASSWORD, "");
	// setConfingInfo(ContentValue.USER_CONFIG_ID, "");
	// setConfingInfo(ContentValue.USER_CONFIG_ROLES, "");
	// setConfingInfo(ContentValue.USER_CONFIG_TOKEN, "");
	// setConfingInfo(ContentValue.USER_CONFIG_NAME, "");
	// setConfingInfo(ContentValue.USER_CONFIG_PHONE, "");
	// setConfingInfo(ContentValue.USER_CONFIG_EMAIL, "");
	// setConfingInfo(ContentValue.USER_CONFIG_LASTLOGINTIME, "");
	// setConfingInfo(ContentValue.USER_CONFIG_AVATAR, "");
	// setConfingInfo(ContentValue.USER_CONFIG_ISLOGIN, false);
	// setConfingInfo(ContentValue.USER_CONFIG_SECURITY, "");
	// setConfingInfo(ContentValue.USER_CONFIG_ISFIRSTLOGIN, true);
	//
	// }

	/**
	 * 设置字符串类型参数
	 */
	public void setConfingInfo(String key, String value) {
		SharedPreferencesUtil.saveString(this, key, value);
	}

	/**
	 * 设置布尔类型参数
	 */
	public void setConfingInfo(String key, boolean value) {
		SharedPreferencesUtil.saveBoolean(this, key, value);
	}

	/**
	 * 设置整数类型参数
	 */
	public void setConfingInfo(String key, int value) {
		SharedPreferencesUtil.saveInteger(this, key, value);
	}

	/**
	 * 获取字符串类型配置文件内容
	 */
	public String getConfigInfo(String key) {
		return SharedPreferencesUtil.getString(this, key, "");
	}

	/**
	 * 获取布尔类型配置文件内容
	 */
	public boolean getConfigInfoBoolean(String key) {
		return SharedPreferencesUtil.getBoolean(this, key, false);
	}

	/**
	 * 获取整数类型配置文件内容
	 */
	public int getConfigInfoInteger(String key) {
		return SharedPreferencesUtil.getInteger(this, key, 0);
	}
}
