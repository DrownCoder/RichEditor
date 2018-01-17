package com.study.xuan.editor.operate.font;

/**
 * Author : xuan.
 * Date : 2017/11/21.
 * Description :input the description of this file.
 */

public class FontParamBuilder{
    private FontParam param;

    public FontParamBuilder() {
        param = new FontParam();
    }

    /**
     * 粗体
     */
    public FontParamBuilder isBold(boolean isbold) {
        param.isBold = isbold;
        return this;
    }
    /**
     * 斜体
     */
    public FontParamBuilder isItalics(boolean isItalics) {
        param.isItalics = isItalics;
        return this;
    }
    /**
     * 下划线
     */
    public FontParamBuilder isUnderLine(boolean isUnderLine) {
        param.isUnderLine = isUnderLine;
        return this;
    }
    /**
     * 中划线
     */
    public FontParamBuilder isCenterLine(boolean isCenterLine) {
        param.isCenterLine = isCenterLine;
        return this;
    }
    /**
     * 是否显示字背景色
     */
    public FontParamBuilder isFontBac(boolean isFontBac) {
        param.isFontBac = isFontBac;
        return this;
    }
    /**
     * 设置字背景色
     */
    public FontParamBuilder fontBac(int fontBac) {
        if (param.isFontBac) {
            param.fontBacColor = fontBac;
        }
        return this;
    }

    /**
     * 设置字的字号
     */
    public FontParamBuilder fontSize(int fontSize) {
        param.fontSize = fontSize;
        return this;
    }

    /**
     * 设置字的字色
     */
    public FontParamBuilder fontColor(int fontColor) {
        param.fontColor = fontColor;
        return this;
    }

    public FontParam build() {
        return param;
    }

    /**
     * 获得字的颜色
     */
    public int getFontColor() {
        return param.fontColor;
    }

    /**
     * 获得字的字号
     */
    public int getFontSize() {
        return param.fontSize;
    }
}
