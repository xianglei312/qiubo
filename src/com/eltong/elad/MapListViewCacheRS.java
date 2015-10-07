package com.eltong.elad;

import com.RTalk.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MapListViewCacheRS {
	private View baseView;

	private TextView textViewa;
	
	private TextView textViewb;

	private ImageView imageView;

    public MapListViewCacheRS(View baseView) {   
        this.baseView = baseView;   
    }   

    public TextView getTextViewa() {   
        if (textViewa == null) {   
            textViewa = (TextView) baseView.findViewById(R.id.Researchlist_Research_title);   
        }   
        return textViewa;   
    }   
    
    public TextView getTextViewb() {   
        if (textViewb == null) {   
            textViewb = (TextView) baseView.findViewById(R.id.Researchlist_member_quota);   
        }   
        return textViewb;   
    }

    public ImageView getImageView() {   
        if (imageView == null) {   
            imageView = (ImageView) baseView.findViewById(R.id.Researchlist_Research_img);   
        }   
        return imageView;   
    }   
}
