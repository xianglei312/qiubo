package com.eltong.elad;

import java.util.List;

import com.RTalk.R;
import com.eltong.elad.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MapListImageAndTextListAdapterADre extends ArrayAdapter<MapListImageAndTextADre> {
	private ListView listView;

	private AsyncImageLoader asyncImageLoader;

	public MapListImageAndTextListAdapterADre(Activity activity,
			List<MapListImageAndTextADre> imageAndTexts, ListView listView) {

		super(activity, 0, imageAndTexts);

		this.listView = listView;

		asyncImageLoader = new AsyncImageLoader();
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Activity activity = (Activity) getContext();

		// Inflate the views from XML

		View rowView = convertView;

		MapListViewCacheADre viewCache;

		if (rowView == null) {

			LayoutInflater inflater = activity.getLayoutInflater();

			rowView = inflater.inflate(R.layout.elad_adhistorylist, null);

			viewCache = new MapListViewCacheADre(rowView);

			rowView.setTag(viewCache);

		} else {

			viewCache = (MapListViewCacheADre) rowView.getTag();

		}

		MapListImageAndTextADre imageAndText = getItem(position);

		// Load the image and set it on the ImageView

		String imageUrl = imageAndText.getvideo_pic();

		ImageView imageView = viewCache.getImageView();

		imageView.setTag(imageUrl);

		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {

						ImageView imageViewByTag = (ImageView) listView
								.findViewWithTag(imageUrl);

						if (imageViewByTag != null) {

							imageViewByTag.setImageDrawable(imageDrawable);

						}

					}

				});

		if (cachedImage == null) {

			imageView.setImageResource(R.drawable.elad_listview_default_img);

		} else {

			imageView.setImageDrawable(cachedImage);

		}

		// Set the text on the TextView

		TextView list_ad_title = viewCache.getTextViewa();

		list_ad_title.setText(imageAndText.gettitle());
		
		TextView list_ad_type = viewCache.getTextViewb();

		list_ad_type.setText(imageAndText.getmedia_type());
		
		TextView list_ad_point = viewCache.getTextViewc();

		list_ad_point.setText(imageAndText.getamount());
		
		TextView list_ad_pointa = viewCache.getTextViewd();

		list_ad_pointa.setText(imageAndText.getmember_quota());

		return rowView;

	}

}
