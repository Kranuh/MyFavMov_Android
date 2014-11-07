package com.timkranen.fonthelpers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class RobotoButton extends Button {
	
	public RobotoButton(Context context) {
        super(context);
    }

    public RobotoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public RobotoButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

}
