package com.study.xuan.editor.operate.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;
import android.widget.TextView;

/**
 * Author : xuan.
 * Date : 2017/11/20.
 * Description :文字环绕图片
 */

public class ImageWrapperSpan implements LeadingMarginSpan.LeadingMarginSpan2 {
    private float imgWidth;
    private float imgHeight;
    private TextView mTv;
    public ImageWrapperSpan(TextView tv,float width,float height) {
        mTv = tv;
        imgWidth = width;
        imgHeight = height;
    }

    @Override
    public int getLeadingMarginLineCount() {
        return (int) Math.ceil(imgHeight/mTv.getLineHeight());
    }

    @Override
    public int getLeadingMargin(boolean first) {
        if (first) {
            return (int) imgWidth;
        } else {
            return 0;
        }
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int
            bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

    }
}
