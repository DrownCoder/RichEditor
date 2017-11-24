package com.study.xuan.editor.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/3.
 * Description :input the description of this file.
 */

public class RichModel {
    public int type;
    public String source;
    public String hint;
    private List<SpanModel> mParmas;

    public RichModel(int type, String s, String df) {
        this.type = type;
        this.source = s;
        this.hint = df;
        this.mParmas = new LinkedList<>();
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

    public List<SpanModel> getSpanList() {
        return mParmas;
    }

}
