package com.study.xuan.editor.model;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.operate.FontParam;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/22.
 * Description :每个span的样式model
 */

public class SpanModel {
    public int spanType;//span样式
    public FontParam param;//span参数
    public List<Object> mSpans;//span样式
    public int paragraphType;
    public String spanString;//字符
    public String imgUrl;//图片
    public int start;//开始处
    public int end;//结束处

    public SpanModel(FontParam param) {
        this.spanType = Const.SPAN_TYPE_FONT;
        this.param = param;
        start = 0;
        end = 0;
    }

    public SpanModel(int paragraphType) {
        this.spanType = Const.SPAN_TYPE_PARAGRAPH;
        this.paragraphType = paragraphType;
    }
}
