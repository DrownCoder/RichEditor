package com.study.xuan.editor.model.panel;

import com.study.xuan.editor.R;

import java.util.ArrayList;
import java.util.List;

import static com.study.xuan.editor.typeholder.ViewType.FONT_BACKGROUND;
import static com.study.xuan.editor.typeholder.ViewType.FONT_BOLD;
import static com.study.xuan.editor.typeholder.ViewType.FONT_ITALICS;
import static com.study.xuan.editor.typeholder.ViewType.FONT_MIDLINE;
import static com.study.xuan.editor.typeholder.ViewType.FONT_UNDERLINE;
import static com.study.xuan.editor.typeholder.ViewType.PANEL_FONT_COLOR;
import static com.study.xuan.editor.typeholder.ViewType.PANEL_FONT_SIZE;
import static com.study.xuan.editor.typeholder.ViewType.PANEL_FONT_STYLE;

/**
 * Author : xuan.
 * Date : 2017/11/19.
 * Description :input the description of this file.
 */

public class PanelFactory {
    private static int FONT_STYLE[] = new int[]{
            R.drawable.bold_select,
            R.drawable.bold_select,
            R.drawable.bold_select,
            R.drawable.bold_select,
            R.drawable.bold_select
    };

    private static int FONT_STYLE_TYPE[] = new int[]{
            FONT_BOLD,
            FONT_ITALICS,
            FONT_UNDERLINE,
            FONT_MIDLINE,
            FONT_BACKGROUND
    };

    private static String FONT_SIZE[] = new String[]{
            "14", "15", "18", "20", "24", "26", "30"
    };

    private static String FONT_COLOR[] = new String[]{
            "#ffffff","#ffffff","#ffffff","#ffffff","#ffffff","#ffffff"
    };

    /**
     * 字体样式楼层
     * 包括：粗体，斜体，下划线，中划线，背景色
     */
    public static List<ModelWrapper> initFontStyleFloor() {
        List<ModelWrapper> datas = new ArrayList<>();
        for (int i = 0; i < FONT_STYLE.length; i++) {
            ModelWrapper wrapper = new ModelWrapper();
            SingleImg img = new SingleImg();
            img.drawablePath = FONT_STYLE[i];
            img.styleType = FONT_STYLE_TYPE[i];
            wrapper.type = PANEL_FONT_STYLE;
            datas.add(wrapper);
        }
        return datas;
    }

    /**
     * 字色楼层
     */
    public static ModelWrapper initFontColorFloor() {
        FontScorll fontColor = new FontScorll();
        fontColor.floorName = "字色";
        fontColor.items = new ArrayList<>();
        for (int i = 0; i < FONT_SIZE.length; i++) {
            SingleText text = new SingleText();
            text.desc = FONT_COLOR[i];
            fontColor.items.add(text);
        }
        ModelWrapper wrapper = new ModelWrapper();
        wrapper.type = PANEL_FONT_COLOR;
        return wrapper;
    }

    /**
     * 字号楼层
     */
    public static ModelWrapper initFontSizeFloor() {
        FontScorll fontColor = new FontScorll();
        fontColor.floorName = "字号";
        fontColor.items = new ArrayList<>();
        for (int i = 0; i < FONT_SIZE.length; i++) {
            SingleText text = new SingleText();
            text.desc = FONT_COLOR[i];
            fontColor.items.add(text);
        }
        ModelWrapper wrapper = new ModelWrapper();
        wrapper.type = PANEL_FONT_SIZE;
        return wrapper;
    }
}
