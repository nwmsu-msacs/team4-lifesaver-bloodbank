package com.example.myapplication.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.myapplication.R;




public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final String TAG = "TextView";

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
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