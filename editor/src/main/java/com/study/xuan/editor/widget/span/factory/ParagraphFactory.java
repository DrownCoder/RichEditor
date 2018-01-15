package com.study.xuan.editor.widget.span.factory;

import android.graphics.Color;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;

import com.study.xuan.editor.common.Const;

/**
 * Author : xuan.
 * Date : 2017/11/24.
 * Description :段落级的span工厂
 */

public class ParagraphFactory implements IParagraphFactory {
    @Override
    public ParagraphStyle createParagraphSpans(int type) {
        ParagraphStyle paragraphStyle = null;
        switch (type) {
            case Const.PARAGRAPH_REFER:
                paragraphStyle = new QuoteSpan(Color.BLACK);
                break;
        }
        return paragraphStyle;
    }
}
