package com.study.xuan.editor.model.panel.event;

/**
 * Author : xuan.
 * Date : 2018/1/12.
 * Description :操作状态
 */

public class BasePanelEvent {
    public boolean isSelected;

    public BasePanelEvent(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
