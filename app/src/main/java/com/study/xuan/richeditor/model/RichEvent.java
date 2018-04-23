package com.study.xuan.richeditor.model;

/**
 * Author : xuan.
 * Date : 2018/4/23.
 * Description :input the description of this file
 */

public class RichEvent {
    public static final int SAVE_SUCCESS = 1;
    public int status;

    public RichEvent(int status) {
        this.status = status;
    }
}
