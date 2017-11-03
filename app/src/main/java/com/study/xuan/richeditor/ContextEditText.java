package com.study.xuan.richeditor;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.widget.EditText;

/**
 * Author : xuan.
 * Date : 2017/11/3.
 * Description :input the description of this file.
 */

public class ContextEditText extends android.support.v7.widget.AppCompatEditText {
    public ContextEditText(Context context) {
        super(context);
    }

    public ContextEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContextEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        Log.i("TAG", menu.toString());
        super.onCreateContextMenu(menu);
    }
}
