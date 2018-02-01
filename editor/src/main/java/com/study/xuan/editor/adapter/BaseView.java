package com.study.xuan.editor.adapter;

import android.content.Context;

import com.study.xuan.editor.model.RichModel;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void onBind(Context context, RichModel model);

}