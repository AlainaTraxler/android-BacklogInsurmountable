package com.example.backloginsurmountable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Guest on 12/2/16.
 */
public class PressStartTextView extends TextView {
    public PressStartTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PressStartTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PressStartTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "PressStart2P.ttf");
            setTypeface(tf);
        }
    }
}
