package com.study.xuan.editor.operate;

import com.study.xuan.editor.common.Const;

import static com.study.xuan.editor.util.EditorUtil.getBooleanString;

/**
 * Author : xuan.
 * Date : 2017/11/21.
 * Description :字体参数
 */

public class FontParam implements Cloneable {
    //粗体
    public boolean isBold;
    //斜体
    public boolean isItalics;
    //下划线
    public boolean isUnderLine;
    //中划线
    public boolean isCenterLine;
    //字体背景色
    public boolean isFontBac;
    public int fontBacColor;
    //字号
    public int fontSize;
    //字色
    public int fontColor;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 返回0101001类型的字符串用于比较参数是否变化
     */
    @Override
    public String toString() {
        return getBooleanString(isBold) + getBooleanString(isItalics) +
                getBooleanString(isUnderLine) + getBooleanString(isCenterLine) +
                getBooleanString(isFontBac) + fontSize + fontColor;
    }

    public String getParamCodes() {
        return getCharCodes() + Const.CODE_FONT_SEPARATOR + getParagraphCodes() + Const
                .CODE_FONT_SEPARATOR + getUpdateCodes();
    }

    private String getUpdateCodes() {
        return "";
    }

    private String getParagraphCodes() {
        return "";
    }

    /**
     * 参数对应转换为code
     */
    private String getCharCodes() {
        return "1"
                + getBooleanString(isBold)
                + getBooleanString(isItalics)
                + getBooleanString(isUnderLine)
                + getBooleanString(isCenterLine)
                + getBooleanString(isFontBac)
                + Const.CODE_CHAR_SEPARATOR + fontSize
                + Const.CODE_CHAR_SEPARATOR + fontColor;
    }
}
