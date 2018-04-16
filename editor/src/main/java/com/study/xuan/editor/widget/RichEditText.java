package com.study.xuan.editor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Author : xuan.
 * Date : 18-4-16.
 * Description : the file description
 */
public class RichEditText extends EditText {
    public interface onSelectionChangedListener {
        void onSelectionChanged(int start, int end);
    }

    private onSelectionChangedListener listener;

    public RichEditText(Context context) {
        super(context);
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnSelectionChangedListener(onSelectionChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (listener != null) {
            listener.onSelectionChanged(selStart, selEnd);
        }
        super.onSelectionChanged(selStart, selEnd);
    }
}
