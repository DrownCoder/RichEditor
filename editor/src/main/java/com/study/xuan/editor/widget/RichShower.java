package com.study.xuan.editor.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.study.xuan.editor.adapter.RichShowAdapter;
import com.study.xuan.editor.model.RichModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/25.
 * Description :input the description of this file
 */

public class RichShower extends RecyclerView {
    private List<RichModel> mData;
    private RichShowAdapter mAdapter;

    public RichShower(Context context) {
        this(context, null);
    }

    public RichShower(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichShower(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mData = new ArrayList<>();
        setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new RichShowAdapter(context, mData);
        setAdapter(mAdapter);
    }

    public void setData(List<RichModel> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }


}
