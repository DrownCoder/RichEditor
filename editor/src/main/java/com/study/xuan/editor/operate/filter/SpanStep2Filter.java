package com.study.xuan.editor.operate.filter;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.util.Log;

import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.study.xuan.editor.common.Const.BASE_LOG;

public class SpanStep2Filter implements TextWatcher, ISpanFilter {
    private int position;
    private List<RichModel> mData;

    public SpanStep2Filter(List<RichModel> mData) {
        this.mData = mData;
    }

    @Override
    public void updatePosition(int position) {
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        // no op
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence instanceof SpannableStringBuilder) {
            SpannableStringBuilder span = (SpannableStringBuilder) charSequence;
            Object[] spans = span.getSpans(0, span.length(), CharacterStyle.class);
            Iterator iterator = null;
            if (mData.get(position).getSpanList() != null) {
                iterator = mData.get(position).getSpanList().iterator();
            }
            boolean needAdd = false;
            for (int i = 0; i < spans.length; i++) {
                SpanModel model;
                if (iterator != null && iterator.hasNext()) {
                    //复用原本的Model，防止重复New
                    model = (SpanModel) iterator.next();
                    if (model.mSpans != null) {
                        model.mSpans.clear();
                    } else {
                        model.mSpans = new ArrayList<>();
                    }
                } else {
                    needAdd = true;
                    model = new SpanModel();
                }
                model.mSpans.add(spans[i]);
                model.end = span.getSpanEnd(spans[i]);
                model.start = span.getSpanStart(spans[i]);
                for (++i; i < spans.length; i++) {
                    if (span.getSpanEnd(spans[i]) == model.end && span.getSpanStart(spans[i]) == model.start) {
                        model.mSpans.add(spans[i]);
                    } else {
                        i--;
                        break;
                    }
                }
                if (needAdd) {
                    mData.get(position).getSpanList().add(model);
                }
            }
            while (!needAdd && iterator != null && iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
            for (SpanModel item : mData.get(position).getSpanList()) {
                Log.i(BASE_LOG, item.mSpans + "start:" + item.start + "end:" + item.end);
            }
        }
        mData.get(position).setSource(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // no op
    }
}