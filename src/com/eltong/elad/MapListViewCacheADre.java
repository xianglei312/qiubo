package com.eltong.elad;

import com.RTalk.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MapListViewCacheADre {
	private View baseView;

	private TextView textViewa;
	
	private TextView textViewb;
	
	private TextView textViewc;
	
	private TextView textViewd;

	private ImageView imageView;

    public MapListViewCacheADre(View baseView) {   
        this.baseView = baseView;   
    }   

    public TextView getTextViewa() {   
        if (textViewa == null) {   
            textViewa = (TextView) baseView.findViewById(R.id.pointtab_adhistory_title);   
        }   
        return textViewa;   
    }   
    
    public TextView getTextViewb() {   
        if (textViewb == null) {   
            textViewb = (TextView) baseView.findViewById(R.id.pointtab_adhistory_type);   
        }   
        return textViewb;   
    }
    
    public TextView getTextViewc() {   
        if (textViewc == null) {   
            textViewc = (TextView) baseView.findViewById(R.id.pointtab_adhistory_point);   
        }   
        return textViewc;   
    }
    
    public TextView getTextViewd() {   
        if (textViewd == null) {   
            textViewd = (TextView) baseView.findViewById(R.id.pointtab_adhistory_pointa);   
        }   
        return textViewd;   
    }

    public ImageView getImageView() {   
        if (imageView == null) {   
            imageView = (ImageView) baseView.findViewById(R.id.pointtab_adhistory_img);   
        }   
        return imageView;   
    }   
}
