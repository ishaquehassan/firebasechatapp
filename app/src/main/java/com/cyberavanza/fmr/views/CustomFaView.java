package com.cyberavanza.fmr.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ishaq Hassan on 5/4/2016.
 */
public class CustomFaView extends TextView {
    public CustomFaView(Context context) {
        super(context);
        this.setCustomFont(context);
    }

    public CustomFaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setCustomFont(context);
    }

    public CustomFaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setCustomFont(context);
    }

    public void setCustomFont(Context context){
        String fontName = "fontawesome-webfont.ttf";
        Typeface face1= Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        setTypeface(face1);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = Html.fromHtml(""+text).toString();
        super.setText(text, type);
    }
}
