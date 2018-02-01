package com.study.xuan.editor.operate.span;

import android.text.SpannableStringBuilder;
import android.util.Log;

import java.util.List;

import static com.study.xuan.editor.common.Const.BASE_LOG;

/**
 * Author : xuan.
 * Date : 2017/11/23.
 * Description :字符串多样式的SpannableString
 */

public class MultiSpannableString extends SpannableStringBuilder {
    public MultiSpannableString() {
    }

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
        StringBuilder builder = new StringBuilder();
        for (Object item : objects) {
            builder.append(item.getClass().toString());
            setSpan(item, start, end, flags);
        }
    }
}
