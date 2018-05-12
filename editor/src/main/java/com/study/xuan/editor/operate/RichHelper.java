package com.study.xuan.editor.operate;

import android.content.Context;
import android.view.View;

import com.study.xuan.editor.callback.onEditorEventListener;
import com.study.xuan.editor.util.RichLog;
import com.study.xuan.editor.widget.Editor;

/**
 * Author : xuan.
 * Date : 2018/5/12.
 * Description :the helper of editor
 */

public class RichHelper {
    private volatile static RichHelper helper;
    private RichBuilder builder;

    private Editor editor;

    public static synchronized RichHelper getInstance() {
        if (helper == null) {
            synchronized (RichHelper.class) {
                if (helper == null) {
                    helper = new RichHelper();
                }
            }
        }
        return helper;
    }

    public RichHelper() {
        builder = RichBuilder.getInstance();
    }

    public void attach(Editor editor) {
        if (editor == null) {
            RichLog.error("the editor is NULL !!!");
        }
        this.editor = editor;
    }

    public Editor buildEditor(Context context) {
        if (context == null) {
            RichLog.error("the context is NULL !!!");
        }
        editor = new Editor(context);
        return editor;
    }

    public void setCallBack(onEditorEventListener callBack) {
        if (callBack == null) {
            RichLog.error("the callback is NULL !!!");
            return;
        }
        editor.setCallback(callBack);
    }

    public void setMoreOperateLayout(View view) {
        if (view == null) {
            RichLog.error("the view is NULL !!!");
            return;
        }
        editor.insertMore(view);
    }

    public void toMarkDown() {
        editor.startMarkDownTask();
    }

    public RichBuilder getBuilder() {
        return builder;
    }

    public void onDestory() {
        editor.destroy();
        builder.destroy();
    }
}
