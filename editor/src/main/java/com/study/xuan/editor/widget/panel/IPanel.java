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
    void reverse(FontParam param);

    void change();

    void reset();

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

    IPanel setRefer();

    IPanel setH1();

    IPanel setH2();

    IPanel setH3();

    IPanel setH4();
}
