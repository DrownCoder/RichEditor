package com.study.xuan.editor.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.study.xuan.editor.R;

/**
 * Author : xuan.
 * Date : 2017/11/17.
 * Description :input the description of this file.
 */

public class EditorPanel extends FrameLayout {
    private RecyclerView mPanel;
    public EditorPanel(@NonNull Context context) {
        this(context, null);
    }

    public EditorPanel(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorPanel(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View root = LayoutInflater.from(context).inflate(R.layout.editor_panel, this);
        initView(root);
    }

    private void initView(View root) {
        mPanel = root.findViewById(R.id.rcy_panel);

    }
}
