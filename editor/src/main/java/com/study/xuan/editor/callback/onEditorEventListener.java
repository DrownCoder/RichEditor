package com.study.xuan.editor.callback;

import com.study.xuan.editor.model.RichModel;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/5/12.
 * Description :the description of this file
 */

public abstract class onEditorEventListener implements onEditorCallback {
    @Override
    public void onLineNumChange(List<RichModel> data) {

    }

    @Override
    public abstract void onPhotoEvent();

    @Override
    public void onMarkDownTaskDoing(int progress, int max) {

    }

    @Override
    public void onMarkDownTaskFinished(String markdown) {

    }
}
