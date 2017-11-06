package com.study.xuan.richeditor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Author : xuan.
 * Date : 2017/11/3.
 * Description :input the description of this file.
 */

public class RichModel {
    public int type;
    public String source;
    public String defaultSource;
    public boolean hasFocus;

    public RichModel(int type, String s, boolean hasfocus, String df) {
        this.type = type;
        this.source = s;
        this.hasFocus = hasfocus;
        this.defaultSource = changeEmpty(df);
    }

    /**
     * 空文本需要空白符做占位符
     */
    @NonNull
    private String changeEmpty(String s) {
        if (TextUtils.isEmpty(s)) {
            s = "                                                                                   ";
        }
        return s;
    }

    public void append(String s) {
        source += s;
    }

    public void setSource(String s) {
        this.source = s;
    }

}
