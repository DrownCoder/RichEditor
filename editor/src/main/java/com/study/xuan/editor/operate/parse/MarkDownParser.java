package com.study.xuan.editor.operate.parse;

import android.graphics.Typeface;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.helper.RichModelHelper;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : parse the span to markdown
 */
public class MarkDownParser extends Parser {
    private Formater formater;

    public MarkDownParser() {
        formater = new MarkDownFormater();
    }

    @Override
    public String toString(List<RichModel> data) {
        if (data == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (RichModel model : data) {
            str.append(model.source);
            RichModelHelper.formatSpan(model.getSpanList());
            //todo 规整区间结束后，将字符串按照区间分割并重新拼接
            for (SpanModel span : model.getSpanList()) {
                if (span.mSpans == null || span.mSpans.size() == 0) {
                    continue;
                }
                for (Object obj : span.mSpans) {
                    if (obj instanceof StyleSpan) {
                        StyleSpan objSpan = (StyleSpan) obj;
                        switch (objSpan.getStyle()) {
                            case Typeface.BOLD:
                                str.replace(span.start, span.end, formater.formatBold(model.source.substring(span.start, span.end)));
                                break;
                            case Typeface.ITALIC:
                                str.append(formater.formatItalics(model.source.substring(span.start, span.end)));
                                break;
                        }
                    } else if (obj instanceof UnderlineSpan) {
                        str.append(formater.formatUnderLine(model.source.substring(span.start, span.end)));
                        break;
                    } else if (obj instanceof StrikethroughSpan) {
                        str.append(formater.formatCenterLine(model.source.substring(span.start, span.end)));
                    }
                }
            }
        }
        return str.toString();
    }

    @Override
    List<RichModel> fromString(String t) {
        return null;
    }

}
