package com.eltong.elad;

import com.RTalk.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MapListViewCacheAD {
	private View baseView;

	private TextView textViewa;
	
	private TextView textViewb;
	
	private TextView textViewc;

	private ImageView imageView;

    public MapListViewCacheAD(View baseView) {   
        this.baseView = baseView;   
    }   

    public TextView getTextViewa() {   
        if (textViewa == null) {   
            textViewa = (TextView) baseView.findViewById(R.id.list_ad_title);   
        }   
        return textViewa;   
    }   
    
    public TextView getTextViewb() {   
        if (textViewb == null) {   
            textViewb = (TextView) baseView.findViewById(R.id.list_ad_type);   
        }   
        return textViewb;   
    }
    
    public TextView getTextViewc() {   
        if (textViewc == null) {   
            textViewc = (TextView) baseView.findViewById(R.id.list_ad_point);   
        }   
        return textViewc;   
    }

    public ImageView getImageView() {   
        if (imageView == null) {   
            imageView = (ImageView) baseView.findViewById(R.id.list_ad_img);   
        }   
        return imageView;   
    }   
}
