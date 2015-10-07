package com.eltong.elad;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;


public class AsyncImageLoader {
	private HashMap<String, SoftReference<Drawable>> imageCache;

	public AsyncImageLoader() {

		imageCache = new HashMap<String, SoftReference<Drawable>>();

	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback imageCallback) {

		if (imageCache.containsKey(imageUrl)) {

			SoftReference<Drawable> softReference = imageCache.get(imageUrl);

			Drawable drawable = softReference.get();

			if (drawable != null) {

				return drawable;

			}

		}

		final Handler handler = new Handler() {

			public void handleMessage(Message message) {

				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);

			}

		};

		new Thread() {

			@Override
			public void run() {

				Drawable drawable = loadImageFromUrl(imageUrl);

				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));

				Message message = handler.obtainMessage(0, drawable);

				handler.sendMessage(message);

			}

		}.start();

		return null;

	}

	public static Drawable loadImageFromUrl(String imageurl) {
		Drawable d = null;
		try {
			URL url = new URL(imageurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(10000);
			conn.connect();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				d = Drawable.createFromStream(is, "src");
				if (is != null) {
					is.close();
					is = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	public interface ImageCallback {

		public void imageLoaded(Drawable imageDrawable, String imageUrl);

	}

}
