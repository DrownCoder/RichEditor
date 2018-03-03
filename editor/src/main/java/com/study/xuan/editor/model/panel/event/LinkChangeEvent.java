package com.study.xuan.editor.model.panel.event;

import com.study.xuan.editor.operate.font.FontParam;

/**
 * Author : xuan.
 * Date : 2018/1/22.
 * Description :Link事件
 */

public class LinkChangeEvent extends BasePanelEvent {
    public String title;
    public FontParam linkParam;

    public LinkChangeEvent(boolean isSelected, FontParam param, String title) {
        super(isSelected);
        linkParam = param;
        this.title = title;
    }
}
