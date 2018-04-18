package com.study.xuan.editor.model;

/**
 * Author : xuan.
 * Date : 18-4-18.
 * Description : 选中的状态信息
 */
public class SelectionInfo {
    public int startIndex;
    public int endIndex;
    public SpanModel startSpan;
    public SpanModel endSpan;

    public void reset() {
        startIndex = 0;
        startIndex = 0;
        startSpan = null;
        endSpan = null;
    }
}
