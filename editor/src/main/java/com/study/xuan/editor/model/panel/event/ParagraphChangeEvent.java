package com.study.xuan.editor.model.panel.event;

/**
 * Author : xuan.
 * Date : 2018/1/12.
 * Description :段落类型的
 */

public class ParagraphChangeEvent extends BasePanelEvent {
    public int pType;

    public ParagraphChangeEvent(boolean isSelected) {
        super(isSelected);
    }
}
