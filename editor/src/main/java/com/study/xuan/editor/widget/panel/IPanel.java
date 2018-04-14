package com.study.xuan.editor.widget.panel;

import android.support.annotation.ColorInt;

import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.paragraph.ParagraphBuilder;

/**
 * Author : xuan.
 * Date : 18-3-6.
 * Description : the file description
 */
public interface IPanel {
    void reverse(FontParam param, int paragraphType);

    void change();

    void resetFont();

    void resetParagraph();

    void reset();

    void setStateChange(onPanelStateChange mStateChange);

    public void setReverse(onPanelReverse mReverse);

    String getType();

    FontParam getFontParam();

    ParagraphBuilder getParagraph();

    IPanel setBold(boolean isSelected);

    IPanel setItalics(boolean isSelected);

    IPanel setUnderLine(boolean isSelected);

    IPanel setCenterLine(boolean isSelected);

    IPanel setFontBac(int fontBac);

    IPanel setFontSize(int fontSize);

    IPanel setFontColor(int fontColor);

    IPanel setUrl(String name, String url);

    IPanel setUrl(String name, String url, @ColorInt int color);

    IPanel setRefer(boolean isRefer);

    IPanel setH1(boolean isH1);

    IPanel setH2(boolean isH2);

    IPanel setH3(boolean isH3);

    IPanel setH4(boolean isH4);

    IPanel showPanel(boolean show);

    boolean isShow();
}
