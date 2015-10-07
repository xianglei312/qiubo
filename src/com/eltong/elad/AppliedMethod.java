package com.eltong.elad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.RTalk.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.TabWidget;

public class AppliedMethod {
	/**
	 * @author XXD
	 * @note 通过HTTP提交服务器，并传递服务器�?��参数，获取XML输入�?
	 */
	public static InputStream doAnswerPostSubmit(String strURL, String param) {
		InputStream is = null;
		try {
			URL url = new URL(strURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.addRequestProperty("User-Agent","AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5/Qubeon");
			connection.setRequestMethod(BasicProperties.HTTP_SUBMIT_METHOD);
			connection.setConnectTimeout(10000);
			PrintWriter pw = new PrintWriter(connection.getOutputStream());
			pw.print(param);
			pw.flush();
			pw.close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

	
	/**
	 * @author XXD
	 * @note 通过HTTP提交服务器，并传递服务器�?��参数，获取XML输入�?
	 */
	public static InputStream doPostSubmit(String strURL, String param) {
		InputStream is = null;
		try {
			String urlName = strURL + "?" + param;   
			URL url =  new URL(urlName);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			//connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.addRequestProperty("User-Agent","AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5/Qubeon");   
			connection.setRequestMethod(BasicProperties.HTTP_REQUEST_METHOD);
			connection.setConnectTimeout(10000);
			//connection.connect(); 
			//int aa =connection.getResponseCode();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
			}
			
			/* for debug */
			
			else {		        
				InputStream err = connection.getErrorStream();
		        StringBuilder sb = new StringBuilder();
		        String line;
		        try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(err, "UTF-8"));
		            while ((line = reader.readLine()) != null) {
		                sb.append(line);
		            }
		        } finally {
		        	err.close();
		        }
		        /* for debug */  
		        //String errMsg = sb.toString();
				//int statusCode = connection.getResponseCode();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}
	
	/**
	 * @author XXD
	 * @note 通过HTTP提交服务器，并传递服务器�?��参数，获取XML输入�?
	 */
	public static InputStream doGetSubmit(String strURL) {
		InputStream is = null;
		try { 
			URL url =  new URL(strURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.addRequestProperty("User-Agent","AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5/Qubeon");   
			connection.setRequestMethod(BasicProperties.HTTP_REQUEST_METHOD);
			connection.setConnectTimeout(10000);
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}


	/**
	 * @author XXD
	 * @note �?��是否有网�?
	 */
	public static final boolean checkNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}

	/**
	 * @author XXD
	 * @note �?��提示�?
	 */
	public static void showExitDialog(final Context context) {
		AlertDialog alertDialog = new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.ad_val_title))
				.setMessage(context.getString(R.string.ad_val_message))
				.setPositiveButton(context.getString(R.string.ad_val_ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								android.os.Process
										.killProcess(android.os.Process.myPid());
							}
						})
				.setNeutralButton(context.getString(R.string.ad_val_back),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								android.os.Process
										.killProcess(android.os.Process.myPid());
							}
						})
				.setNegativeButton(context.getString(R.string.ad_val_cancle),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								return;
							}
						}).create();
		alertDialog.show();
	}

	/**
	 * @author XXD
	 * @note 提交等待提示�?
	 */
	public static ProgressDialog showWaitDialog(Context context) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setTitle(context.getString(R.string.pd_val_title));
		pd.setMessage(context.getString(R.string.pd_val_message));
		pd.setCancelable(true);
		pd.show();
		return pd;
	}

	/**
	 * @author XXD
	 * @note 去除Tab底部横条
	 */
	public static void setTabBottom(Context context, TabWidget tabWidget) {
		Field mBottomLeftStrip;
		Field mBottomRightStrip;
		if (Integer.valueOf(Build.VERSION.SDK) <= 7) {
			try {
				mBottomLeftStrip = tabWidget.getClass().getDeclaredField(
						"mBottomLeftStrip");
				mBottomRightStrip = tabWidget.getClass().getDeclaredField(
						"mBottomRightStrip");
				if (!mBottomLeftStrip.isAccessible()) {
					mBottomLeftStrip.setAccessible(true);
				}
				if (!mBottomRightStrip.isAccessible()) {
					mBottomRightStrip.setAccessible(true);
				}
				mBottomLeftStrip.set(tabWidget, context.getResources()
						.getDrawable(android.R.color.transparent));
				mBottomRightStrip.set(tabWidget, context.getResources()
						.getDrawable(android.R.color.transparent));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				mBottomLeftStrip = tabWidget.getClass().getDeclaredField(
						"mLeftStrip");
				mBottomRightStrip = tabWidget.getClass().getDeclaredField(
						"mRightStrip");
				if (!mBottomLeftStrip.isAccessible()) {
					mBottomLeftStrip.setAccessible(true);
				}
				if (!mBottomRightStrip.isAccessible()) {
					mBottomRightStrip.setAccessible(true);
				}
				mBottomLeftStrip.set(tabWidget, context.getResources()
						.getDrawable(android.R.color.transparent));
				mBottomRightStrip.set(tabWidget, context.getResources()
						.getDrawable(android.R.color.transparent));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author XXD
	 * @note 动�?设置公共分段按钮高度
	 */
	public static int getSegmentHeight(Context context, int height) {
		int segmentHeight = 0;
		int type = context.getResources().getConfiguration().orientation;
		if (type == Configuration.ORIENTATION_LANDSCAPE) {
			segmentHeight = height * BasicProperties.SEGMENT_HEIGHT / 480;
		} else if (type == Configuration.ORIENTATION_PORTRAIT) {
			segmentHeight = height * BasicProperties.SEGMENT_HEIGHT / 854;
		}
		return segmentHeight;
	}

	/**
	 * @author XXD
	 * @note dip转换PX
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * @author 赵靖�?
	 * @note PX转换dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public static final Bitmap getBitmap(Context context, String strURL) {
		// Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.listview_default);
		if (!BasicProperties.XML_VALUE7.equals(strURL)) {
			try {
				URL url = new URL(strURL);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);
				conn.setConnectTimeout(10000);
				conn.connect();
				for (int i = 0; i < 5; i++) {
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						Bitmap bm = BitmapFactory.decodeStream(is, null, null);
						if (is != null) {
							is.close();
							is = null;
						}
						if (conn != null) {
							conn.disconnect();
							conn = null;
						}
						return bm;
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
