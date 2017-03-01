package com.cyberavanza.fmr.model;

import android.view.View;

/**
 * Created by Ishaq Hassan on 12/2/2016.
 */

public class ChatListItem {
    String image;
    String name;
    String message;
    String time;
    View.OnClickListener clickListener = null;

    public ChatListItem(String image, String name, String message, String time) {
        this.image = image;
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public ChatListItem(String image, String name, String message, String time,View.OnClickListener listener) {
        this.image = image;
        this.name = name;
        this.message = message;
        this.time = time;
        this.clickListener = listener;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
