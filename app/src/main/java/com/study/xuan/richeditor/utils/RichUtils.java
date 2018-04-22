package com.study.xuan.richeditor.utils;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;


/**
 * Author : xuan.
 * Date : 2018/4/22.
 * Description :input the description of this file
 */

public class RichUtils {
    public static void setTextStyle(Context context, TextView v, int style) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setTextAppearance(style);
        }else{
            v.setTextAppearance(context, style);
        }
    }
}
