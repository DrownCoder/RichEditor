package com.study.xuan.editor.model;

/**
 * Author : xuan.
 * Date : 2017/11/3.
 * Description :input the description of this file.
 */

public class RichModel {
    public static final int TYPE_EDIT = 0;
    public static final int TYPE_IMG = 1;

    public int type;
    public String source;
    public String hint;

    public RichModel(int type, String s, String df) {
        this.type = type;
        this.source = s;
        this.hint = df;
    }

    public RichModel(int type, String url) {
        this.type = type;
        this.source = url;
    }


    public void append(String s) {
        source += s;
    }

    public void setSource(String s) {
        this.source = s;
    }

}
