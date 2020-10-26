package com.example.myapplication.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.myapplication.R;




public class CustomButton extends androidx.appcompat.widget.AppCompatButton {
    private static final String TAG = "TextView";

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
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

