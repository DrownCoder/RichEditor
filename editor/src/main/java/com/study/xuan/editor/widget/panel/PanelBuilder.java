package com.study.xuan.editor.widget.panel;

import com.study.xuan.editor.operate.font.FontParamBuilder;

/**
 * Author : xuan.
 * Date : 18-3-5.
 * Description : 操作面板
 */
public class PanelBuilder implements IPanel {
    public static final String TYPE_FONT = "FONT";
    public static final String TYPE_PARAGRAPH = "PARAGRAPH";
    private String type;

    private FontParamBuilder fontParamBuilder;

    private onPanelStateChange mStateChange;

    public void setStateChange(onPanelStateChange mStateChange) {
        this.mStateChange = mStateChange;
    }

    @Override
    public void change() {
        if (mStateChange != null) {
            mStateChange.onStateChanged(true);
        }
    }

    @Override
    public void reset() {
        fontParamBuilder.reset();
    }

    @Override
    public IPanel setBold(boolean isSelected) {
        fontParamBuilder.isBold(isSelected);
        return this;
    }

    @Override
    public IPanel setItalics(boolean isSelected) {
        return null;
    }

    @Override
    public IPanel setUnderLine(boolean isSelected) {
        return null;
    }

    @Override
    public IPanel setCenterLine(boolean isSelected) {
        return null;
    }

    @Override
    public IPanel setFontBac(int fontBac) {
        return null;
    }

    @Override
    public IPanel setFontSize(int fontSize) {
        return null;
    }

    @Override
    public IPanel setFontColor(int fontColor) {
        return null;
    }

    @Override
    public IPanel setUrl(String url) {
        return null;
    }

    @Override
    public IPanel setRefer(boolean isRefer) {
        return null;
    }
}
