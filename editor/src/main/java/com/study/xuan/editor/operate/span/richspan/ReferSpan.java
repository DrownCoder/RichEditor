package com.study.xuan.editor.operate.span.richspan;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * Author : xuan.
 * Date : 2018/1/16.
 * Description :引用span
 */

public class ReferSpan implements LeadingMarginSpan {
    private static final int STRIPE_WIDTH = 20;
    private static final int GAP_WIDTH = 2;

    private final int mColor;
    private final int mTextPadding;

    public ReferSpan() {
        super();
        mColor = 0xff0000ff;
        mTextPadding = 10;
    }

    public ReferSpan(@ColorInt int color) {
        super();
        mColor = color;
        mTextPadding = 10;
    }

    public ReferSpan(@ColorInt int color, int textPadding) {
        super();
        mColor = color;
        mTextPadding = textPadding;
    }


    public int getColor() {
        return mColor;
    }

    public int getLeadingMargin(boolean first) {
        return STRIPE_WIDTH + GAP_WIDTH + mTextPadding;
    }

    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(mColor);

        c.drawRect(x, top, x + dir * STRIPE_WIDTH, bottom, p);

        p.setStyle(style);
        p.setColor(color);
    }
}
