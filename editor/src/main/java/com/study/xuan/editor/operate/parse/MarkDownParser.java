package com.study.xuan.editor.operate.parse;

import android.graphics.Typeface;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.helper.RichModelHelper;
import com.study.xuan.editor.operate.span.ReferSpan;
import com.study.xuan.editor.operate.span.URLSpanNoUnderline;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : span转换成MarkDown语法的String
 */
public class MarkDownParser extends Parser {
    private Formater formater;
    private SparseArray<String> pool;
    private int priority = 0;

    MarkDownParser() {
        formater = new MarkDownFormater();
        pool = new SparseArray<>();
    }

    @Override
    public String toString(List<RichModel> data) {
        if (data == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (RichModel model : data) {
            RichModelHelper.formatSpan(model.getSpanList());
            //规整区间结束后，将字符串按照区间分割并重新拼接,按照优先级将字符串分割成一小段
            // ，（因为可能存在顺序不是递增的，所以需要用优先级），最后再按照优先级将拼接成整体。

            //拼接字符级别markdown
            if (model.getSpanList() == null || model.getSpanList().size() == 0) {
                str.append(model.source).append("\n");
            } else {
                SpanModel item;
                int lastEnd = 0;
                for (int i = 0; i < model.getSpanList().size(); i++) {
                    item = model.getSpanList().get(i);
                    if (lastEnd != item.start) {
                        pool.put(priority++, model.source.substring(lastEnd, item.start));
                        //str.append(model.source.substring(lastEnd, item.start));
                    } else {
                        //两个相邻的span之间markdown需要用空格隔开
                        pool.put(priority++, " ");
                        //str.append(" ");
                    }
                    pool.put(item.start, spanToMarkDown(item.mSpans, model.source, item.start, item.end));
                    priority = item.start + 1;
                    //str.append(spanToMarkDown(item.mSpans, model.source, item.start, item.end));
                    lastEnd = item.end;
                }
                pool.put(priority++, model.source.substring(lastEnd, model.source.length()));
                //str.append(model.source.substring(lastEnd, model.source.length())).append("\n");
                for (int t = 0; t < pool.size(); t++) {
                    str.append(pool.valueAt(t));
                }
                pool.clear();
            }
            //拼接段落级别markdown语法
            if (model.isParagraphStyle) {
                str.replace(0, str.length(), paragraphToMarkDown(model.paragraphSpan.mSpans, str));
            }
            str.append("\n");
        }
        return str.toString();
    }

    private String paragraphToMarkDown(List<Object> mSpans, StringBuilder source) {
        if (mSpans == null || mSpans.size() == 0) {
            return source.toString();
        }
        String str = source.toString();
        for (Object obj : mSpans) {
            if (obj instanceof ReferSpan) {
                str = formater.formatRefer(str);
            } else if (obj instanceof AlignmentSpan) {
                //markdown是不支持居中的！！！，后期考虑注释掉
               // str = formater.formatCenter(str);
            } else if (obj instanceof AbsoluteSizeSpan) {
                AbsoluteSizeSpan sizeSpan = (AbsoluteSizeSpan) obj;
                switch (sizeSpan.getSize()) {
                    case Const.T1_SIZE:
                        str = formater.formatH1(str);
                        break;
                    case Const.T2_SIZE:
                        str = formater.formatH2(str);
                        break;
                    case Const.T3_SIZE:
                        str = formater.formatH3(str);
                        break;
                    case Const.T4_SIZE:
                        str = formater.formatH4(str);
                        break;
                }
            }
        }
        return str;
    }

    @Override
    List<RichModel> fromString(String t) {
        return null;
    }


    private String spanToMarkDown(List<Object> mSpans, String source, int start, int end) {
        if (mSpans == null || mSpans.size() == 0) {
            return source.substring(start, end);
        }
        String str = source.substring(start, end);
        for (Object obj : mSpans) {
            if (obj instanceof StyleSpan) {
                StyleSpan objSpan = (StyleSpan) obj;
                switch (objSpan.getStyle()) {
                    case Typeface.BOLD:
                        str = formater.formatBold(str);
                        break;
                    case Typeface.ITALIC:
                        str = formater.formatItalics(str);
                        break;
                }
            } else if (obj instanceof UnderlineSpan) {
                //markdown是不支持下划线的！！！，后期考虑注释掉
                str = formater.formatUnderLine(str);
            } else if (obj instanceof StrikethroughSpan) {
                str = formater.formatCenterLine(str);
            } else if (obj instanceof URLSpanNoUnderline) {
                URLSpanNoUnderline url = (URLSpanNoUnderline) obj;
                str = formater.formatLink(str, url.getURL());
            }
        }
        return str;
    }

}
