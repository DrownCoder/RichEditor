package com.study.xuan.editor.callback;

import com.study.xuan.editor.model.RichModel;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/5/11.
 * Description :the callback of the editor
 */

public interface onEditorCallback {
    void onLineNumChange(List<RichModel> data);

    void onPhotoEvent();

    void onMarkDownTaskDoing(int progress, int max);

    void onMarkDownTaskFinished(String markdown);
}
