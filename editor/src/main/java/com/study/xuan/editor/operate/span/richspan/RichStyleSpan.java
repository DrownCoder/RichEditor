package com.study.xuan.editor.operate.span.richspan;

import android.os.Parcel;
import android.text.style.StyleSpan;

/**
 * Author : xuan.
 * Date : 18-4-11.
 * Description : the file description
 */
public class RichStyleSpan extends StyleSpan {
    public RichStyleSpan(int style) {
        super(style);
    }

    public RichStyleSpan(Parcel src) {
        super(src);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RichStyleSpan) {
            RichStyleSpan span = (RichStyleSpan) obj;
            if (getStyle() == span.getStyle()) {
                return true;
            }
        }
        return false;
    }
}
