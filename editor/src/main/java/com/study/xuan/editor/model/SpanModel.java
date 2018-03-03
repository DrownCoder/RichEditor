package com.study.xuan.editor.model;

import com.study.xuan.editor.operate.font.FontParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/22.
 * Description :每个span的样式model
 */

public class SpanModel {
    public List<Object> mSpans;//span样式
    public int paragraphType = -1;
    public String spanString;//字符
    public String imgUrl;//图片
    public int start;//开始处
    public int end;//结束处
    public String code;

    public SpanModel(FontParam param) {
        start = 0;
        end = 0;
    }

    public SpanModel() {
        mSpans = new ArrayList<>();
        start = 0;
        end = 0;
    }

    public SpanModel(int paragraphType) {
        this.paragraphType = paragraphType;
    }

    @Override
    public String toString() {
        return code;
    }
}
