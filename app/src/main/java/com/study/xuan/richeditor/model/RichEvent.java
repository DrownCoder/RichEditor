package com.study.xuan.richeditor.model;

import com.study.xuan.editor.model.RichModel;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/23.
 * Description :input the description of this file
 */

public class RichEvent {
    public static final int SAVE_SUCCESS = 1;//保存成功
    public static final int TRANS_SUCCESS = 2;//转换成功
    public static final int JUMP_DETAIL = 3;//跳转bundle
    public int status;
    public List<RichModel> mData;
    public String content;


    public RichEvent(int status) {
        this.status = status;
    }

    public void setData(List<RichModel> data) {
        this.mData = data;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
