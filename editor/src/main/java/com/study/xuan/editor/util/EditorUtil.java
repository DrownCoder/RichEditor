package com.study.xuan.editor.util;

import android.content.Context;

/**
 * Author : xuan.
 * Date : 2017/11/27.
 * Description :input the description of this file.
 */

public class EditorUtil {
    /**
     * true返回1，false返回0
     */
    public static String getBooleanString(boolean b) {
        return b ? "1" : "0";
    }

    /**
     * 0对应false,1对应true
     */
    public static boolean getCharBoolean(char c) {
        return c != '0';
    }

    /**
     * 获取资源中的颜色
     */
    public static int getResourcesColor(Context context,int color) {

        int ret = 0x00ffffff;
        try {
            ret = context.getResources().getColor(color);
        } catch (Exception e) {
        }
        return ret;
    }
}
