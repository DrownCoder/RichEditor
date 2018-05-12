package com.study.xuan.editor.util;

import android.util.Log;

import static com.study.xuan.editor.common.Const.BASE_LOG;

/**
 * Author : xuan.
 * Date : 18-3-15.
 * Description : the file description
 */
public class RichLog {
    public static void log(String log) {
        Log.i(BASE_LOG, log);
    }

    public static void error(String log) {
        Log.e(BASE_LOG, log);
    }
}
