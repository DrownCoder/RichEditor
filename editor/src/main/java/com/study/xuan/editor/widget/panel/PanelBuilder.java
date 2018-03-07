package com.study.xuan.editor.widget.panel;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.font.FontParamBuilder;
import com.study.xuan.editor.operate.paragraph.ParagraphBuilder;

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
    private ParagraphBuilder paragraphBuilder;

    private onPanelStateChange mStateChange;

    public PanelBuilder() {
        fontParamBuilder = new FontParamBuilder();
        paragraphBuilder = new ParagraphBuilder();
    }

    public void setStateChange(onPanelStateChange mStateChange) {
        this.mStateChange = mStateChange;
    }

    @Override
    public void change() {
        if (mStateChange != null) {
            mStateChange.onStateChanged();
        }
    }

    @Override
    public void reset() {
        fontParamBuilder.reset();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public FontParam getFontParam() {
        return fontParamBuilder.build();
    }

    @Override
    public IPanel setBold(boolean isSelected) {
        type = TYPE_FONT;
        fontParamBuilder.isBold(isSelected);
        return this;
    }

    @Override
    public IPanel setItalics(boolean isSelected) {
        type = TYPE_FONT;
        fontParamBuilder.isItalics(isSelected);
        return this;
    }

    @Override
    public IPanel setUnderLine(boolean isSelected) {
        type = TYPE_FONT;
        fontParamBuilder.isUnderLine(isSelected);
        return this;
    }

    @Override
    public IPanel setCenterLine(boolean isSelected) {
        type = TYPE_FONT;
        fontParamBuilder.isCenterLine(isSelected);
        return this;
    }

    @Override
    public IPanel setFontBac(int fontBac) {
        type = TYPE_FONT;
        fontParamBuilder.fontBac(fontBac);
        return this;
    }

    @Override
    public IPanel setFontSize(int fontSize) {
        type = TYPE_FONT;
        fontParamBuilder.fontSize(fontSize);
        return this;
    }

    @Override
    public IPanel setFontColor(int fontColor) {
        type = TYPE_FONT;
        fontParamBuilder.fontColor(fontColor);
        return this;
    }

    @Override
    public IPanel setUrl(String name, String url) {
        type = TYPE_FONT;
        fontParamBuilder.url(name, url);
        return this;
    }

    @Override
    public IPanel setRefer() {
        type = TYPE_PARAGRAPH;
        paragraphBuilder.setType(Const.PARAGRAPH_REFER);
        return this;
    }

    @Override
    public IPanel setH1() {
        type = TYPE_PARAGRAPH;
        paragraphBuilder.setType(Const.PARAGRAPH_T1);
        return this;
    }

    @Override
    public IPanel setH2() {
        type = TYPE_PARAGRAPH;
        paragraphBuilder.setType(Const.PARAGRAPH_T2);
        return this;
    }

    @Override
    public IPanel setH3() {
        type = TYPE_PARAGRAPH;
        paragraphBuilder.setType(Const.PARAGRAPH_T3);
        return this;
    }

    @Override
    public IPanel setH4() {
        type = TYPE_PARAGRAPH;
        paragraphBuilder.setType(Const.PARAGRAPH_T4);
        return this;
    }
}
