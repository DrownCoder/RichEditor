package com.study.xuan.editor.operate.span.factory;

import android.graphics.Color;
import android.text.Layout;
import android.text.style.AlignmentSpan;
import android.text.style.ParagraphStyle;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.operate.span.richspan.ReferSpan;

/**
 * Author : xuan.
 * Date : 2017/11/24.
 * Description :段落级的span工厂
 */

public class ParagraphFactory implements IParagraphFactory {
    @Override
    public ParagraphStyle createParagraphSpans(String code) {
        int iCode = Integer.valueOf(code.substring(1));
        ParagraphStyle paragraphStyle = null;
        switch (iCode) {
            case Const.PARAGRAPH_REFER:
                paragraphStyle = new ReferSpan(Color.parseColor("#b4b4b4"));
                break;
            case Const.PARAGRAPH_T1:
            case Const.PARAGRAPH_T2:
            case Const.PARAGRAPH_T3:
            case Const.PARAGRAPH_T4:
                paragraphStyle = new AlignmentSpan() {
                    @Override
                    public Layout.Alignment getAlignment() {
                        return Layout.Alignment.ALIGN_CENTER;
                    }
                };
                break;
        }
        return paragraphStyle;
    }
}
