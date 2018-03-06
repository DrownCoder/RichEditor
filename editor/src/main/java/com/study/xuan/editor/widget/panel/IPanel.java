package com.study.xuan.editor.widget.panel;

/**
 * Author : xuan.
 * Date : 18-3-6.
 * Description : the file description
 */
public interface IPanel {
    void change();

    void reset();
    
    IPanel setBold(boolean isSelected);

    IPanel setItalics(boolean isSelected);

    IPanel setUnderLine(boolean isSelected);

    IPanel setCenterLine(boolean isSelected);

    IPanel setFontBac(int fontBac);

    IPanel setFontSize(int fontSize);

    IPanel setFontColor(int fontColor);

    IPanel setUrl(String url);

    IPanel setRefer(boolean isRefer);
}
