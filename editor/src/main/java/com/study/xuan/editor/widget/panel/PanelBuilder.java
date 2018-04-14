package com.study.xuan.editor.widget.panel;

import android.graphics.Color;
import android.support.annotation.ColorInt;

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
    public static final String TYPE_PANEL = "PANEL";
    public static final String TYPE_FONT = "FONT";
    public static final String TYPE_LINK = "LINK";
    public static final String TYPE_PARAGRAPH = "PARAGRAPH";
    private String type;
    private boolean show;

    private FontParamBuilder fontParamBuilder;
    private ParagraphBuilder paragraphBuilder;

    private onPanelStateChange mStateChange;
    private onPanelReverse mReverse;

    public PanelBuilder() {
        fontParamBuilder = new FontParamBuilder();
        paragraphBuilder = new ParagraphBuilder();
    }

    @Override
    public void setStateChange(onPanelStateChange mStateChange) {
        this.mStateChange = mStateChange;
    }

    public void setReverse(onPanelReverse mReverse) {
        this.mReverse = mReverse;
    }

    @Override
    public void reverse(FontParam param, int paragraphType) {
        /*if (param != null) {
            fontParamBuilder.setParam(param);
        }else {
            fontParamBuilder.reset();
        }*/
        paragraphBuilder.setType(paragraphType);
        if (mReverse != null) {
            mReverse.onReverse(param, paragraphType);
        }
    }

    @Override
    public void change() {
        if (mStateChange != null) {
            mStateChange.onStateChanged();
        }
    }

    @Override
    public void resetFont() {
        fontParamBuilder.reset();
        reverse(fontParamBuilder.build(), paragraphBuilder.type);
    }

    @Override
    public void resetParagraph() {
        fontParamBuilder.reset();
        reverse(fontParamBuilder.build(), paragraphBuilder.type);
    }

    @Override
    public void reset() {
        paragraphBuilder.reset();
        fontParamBuilder.reset();
        reverse(fontParamBuilder.build(), paragraphBuilder.type);
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
    public ParagraphBuilder getParagraph() {
        return paragraphBuilder;
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
        type = TYPE_LINK;
        fontParamBuilder.fontColor(Color.parseColor("#3194D0"));
        fontParamBuilder.url(name, url);
        return this;
    }

    @Override
    public IPanel setUrl(String name, String url, @ColorInt int color) {
        type = TYPE_LINK;
        fontParamBuilder.fontColor(color);
        fontParamBuilder.url(name, url);
        return this;
    }

    @Override
    public IPanel setRefer(boolean isRefer) {
        if (isRefer) {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_REFER);
        } else {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_NONE);
        }
        return this;
    }

    @Override
    public IPanel setH1(boolean isH1) {
        if (isH1) {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_T1);
        } else {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_NONE);
        }
        return this;
    }

    @Override
    public IPanel setH2(boolean isH2) {
        if (isH2) {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_T2);
        } else {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_NONE);
        }
        return this;
    }

    @Override
    public IPanel setH3(boolean isH3) {
        if (isH3) {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_T3);
        } else {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_NONE);
        }
        return this;
    }

    @Override
    public IPanel setH4(boolean isH4) {
        if (isH4) {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_T4);
        } else {
            type = TYPE_PARAGRAPH;
            paragraphBuilder.setType(Const.PARAGRAPH_NONE);
        }

        return this;
    }

    @Override
    public IPanel showPanel(boolean show) {
        type = TYPE_PANEL;
        this.show = show;
        return this;
    }

    @Override
    public boolean isShow() {
        return show;
    }
}
