package com.cyberavanza.fmr.base;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class BasicFunctionalities {

    public static ProgressDialog buildProgressDialog(String message, Context context){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(message);
        pd.setCancelable(false);
        return pd;
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
