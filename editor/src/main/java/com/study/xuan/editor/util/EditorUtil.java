package com.study.xuan.editor.util;

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
}
