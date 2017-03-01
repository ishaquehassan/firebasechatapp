package com.cyberavanza.fmr.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Ishaq Hassan on 5/4/2016.
 */
public class CustomFaButtonView extends Button {
    public CustomFaButtonView(Context context) {
        super(context);
        this.setCustomFont(context);
    }

    public CustomFaButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setCustomFont(context);
    }

    public CustomFaButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setCustomFont(context);
    }

    public void setCustomFont(Context context){
        String fontName = "fontawesome-webfont.ttf";
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        setTypeface(face1);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = Html.fromHtml(""+text).toString();
        super.setText(text, type);
    }
}
