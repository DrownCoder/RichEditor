package com.study.xuan.editor.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Author : xuan.
 * Date : 2018/4/25.
 * Description :input the description of this file
 */

public class RichShower extends RecyclerView {
    public RichShower(Context context) {
        this(context, null);
    }

    public RichShower(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichShower(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context));
    }


}
