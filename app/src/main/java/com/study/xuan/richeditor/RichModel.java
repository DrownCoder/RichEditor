package com.study.xuan.richeditor;

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
        this.defaultSource = df;
    }

    public void append(String s) {
        source += s;
    }

    public void setSource(String s) {
        this.source = s;
    }

}
