package com.study.xuan.editor.adapter;

/**
 * Author : xuan.
 * Date : 2018/1/23.
 * Description :input the description of this file.
 */

public interface RichContract {
    interface IVHolder extends BaseView<IPresenter> {
        void setImage(String url);
    }

    interface IPresenter extends BasePresenter{

    }
}
