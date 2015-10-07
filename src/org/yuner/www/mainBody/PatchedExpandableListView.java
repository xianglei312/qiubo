package org.yuner.www.mainBody;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class PatchedExpandableListView extends ExpandableListView {

	  public PatchedExpandableListView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }

	  @Override
	  protected void onDetachedFromWindow() {
	    try {
	      super.onDetachedFromWindow();
	    } catch(Exception iae) {
	     
	    }
	  }
}