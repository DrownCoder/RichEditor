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
    public boolean isNewSpan;//是否有新的span
    public SpanModel newSpan;
    public boolean isParagraphStyle;//是否有段落格式
    public SpanModel paragraphSpan;
    public int curIndex;//当前的光标


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

    public void setNewSpan(SpanModel model) {
        this.isNewSpan = true;
        this.newSpan = model;
    }

    public void setNoNewSpan() {
        this.isNewSpan = false;
        this.newSpan = null;
    }

    public void setParagraphSpan(SpanModel model) {
        this.isParagraphStyle = true;
        this.paragraphSpan = model;
    }

    public void setNoParagraphSpan() {
        this.isParagraphStyle = false;
        this.paragraphSpan = null;
    }

    /**
     * 增加链接
     */
    public void addLink(String title, SpanModel model) {
        model.start = source.length();
        source += title;
        model.end = source.length();
        mParmas.add(model);
    }

}
