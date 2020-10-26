package com.example.myapplication.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.example.myapplication.R;



public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "TextView";

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        setCustomFont(ctx);
        a.recycle();
    }
    public boolean setCustomFont(Context ctx) {
        Typeface typeface = null;
        if (typeface == null) {
            typeface = Typeface.createFromAsset(ctx.getAssets(), "customfont.otf");
        }

        setTypeface(typeface);
        return true;
    }
}