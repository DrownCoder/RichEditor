package com.study.xuan.editor.model;

import java.util.LinkedList;
import java.util.List;

import static com.study.xuan.editor.common.Const.TYPE_EDIT;
import static com.study.xuan.editor.common.Const.TYPE_IMG;

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

    public RichModel() {
        mParmas = new LinkedList<>();
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

    public void addSpanModel(SpanModel model) {
        if (mParmas == null) {
            mParmas = new LinkedList<>();
        }
        mParmas.add(model);
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

    public RichModel isText() {
        this.type = TYPE_EDIT;
        return this;
    }

    public RichModel isImg() {
        this.type = TYPE_IMG;
        return this;
    }

    /**
     * 增加链接
     */
    public void addSpanModel(String title, SpanModel model) {
        model.start = curIndex;
        StringBuilder builder = new StringBuilder(source);
        builder.insert(curIndex, title);
        source = builder.toString();
        model.end = curIndex + title.length();
        mParmas.add(model);
        curIndex += title.length();
    }

}
