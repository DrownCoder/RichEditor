package com.study.xuan.editor.callback;

import com.study.xuan.editor.model.RichModel;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/22.
 * Description :input the description of this file
 */

public interface onEditorEvent {
    void onChange(List<RichModel> data);
}
