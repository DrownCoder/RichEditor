package com.study.xuan.editor.model.panel.state;

/**
 * Author : xuan.
 * Date : 2018/1/22.
 * Description :Link事件
 */

public class LinkChangeEvent extends BasePanelEvent {
    public String linkUrl;
    public String title;
    public LinkChangeEvent(boolean isSelected, String name, String url) {
        super(isSelected);
        this.title = name;
        this.linkUrl = url;
    }
}
