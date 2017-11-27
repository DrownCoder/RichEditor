package com.study.xuan.editor.widget.span;

import android.text.SpannableStringBuilder;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/23.
 * Description :字符串多样式的SpannableString
 */

public class MultiSpannableString extends SpannableStringBuilder {
    public MultiSpannableString(CharSequence source) {
        super(source);
    }

    /**
     * 支持多样式的SpannableString
     */
    public void setMultiSpans(int start, int end, int flags, Object... objects) {
        for (Object item : objects) {
            setSpan(item, start, end, flags);
        }
    }

    public void setMultiSpans(List<Object> objects, int start, int end, int flags) {
        for (Object item : objects) {
            setSpan(item, start, end, flags);
        }
    }
}
