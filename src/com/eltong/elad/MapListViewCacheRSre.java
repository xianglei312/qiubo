package com.eltong.elad;

import com.RTalk.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MapListViewCacheRSre {
	private View baseView;

	private TextView textViewa;
	
	private TextView textViewb;
	
	private TextView textViewc;

	private ImageView imageView;

    public MapListViewCacheRSre(View baseView) {   
        this.baseView = baseView;   
    }   

    public TextView getTextViewa() {   
        if (textViewa == null) {   
            textViewa = (TextView) baseView.findViewById(R.id.pointtab_researchhistory_title);   
        }   
        return textViewa;   
    }   
    
    public TextView getTextViewb() {   
        if (textViewb == null) {   
            textViewb = (TextView) baseView.findViewById(R.id.pointtab_researchhistory_point);   
        }   
        return textViewb;   
    }
    
    public TextView getTextViewc() {   
        if (textViewc == null) {   
            textViewc = (TextView) baseView.findViewById(R.id.pointtab_researchhistory_pointa);   
        }   
        return textViewc;   
    }

    public ImageView getImageView() {   
        if (imageView == null) {   
            imageView = (ImageView) baseView.findViewById(R.id.pointtab_researchhistory_img);   
        }   
        return imageView;   
    }   
}
