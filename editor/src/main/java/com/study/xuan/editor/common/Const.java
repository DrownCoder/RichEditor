package com.study.xuan.editor.common;

/**
 * Author : xuan.
 * Date : 2017/11/17.
 * Description :全局类型
 */

public class Const {
    public static final String BASE_LOG = "RichEditor";                     //Log信息

    /**
     * panel面板类型
     */
    public static final int PANEL_FONT_STYLE = 0;                           //字体样式
    public static final int PANEL_FONT_SIZE = 1;                            //字体大小
    public static final int PANEL_FONT_COLOR = 2;                           //字体颜色
    public static final int PANEL_HEADER = 3;                               //标题样式

    /**
     * 段落类型
     */
    public static final int PARAGRAPH_REFER = 0;                            //引用类型

    /**
     * 具体字体样式
     */
    public static final int FONT_BOLD = 0;                                  //粗体
    public static final int FONT_ITALICS = 1;                               //斜体
    public static final int FONT_UNDERLINE = 2;                             //下划线
    public static final int FONT_MIDLINE = 3;                               //中划线
    public static final int FONT_BACKGROUND = 4;                            //背景色
    public static final int FONT_SIZE = 5;                                  //字号
    public static final int FONT_FOREGROUND = 6;                            //字色

    /**
     * 数据样式
     */
    public static final int TYPE_EDIT = 0;                                   //普通编辑框
    public static final int TYPE_IMG = 1;                                    //一行图片

    public static final String CODE_CHAR_SEPARATOR = "%";                    //char类型的code的分隔符
    public static final String CODE_FONT_SEPARATOR = "|";                    //param的分隔符

    /**
     * span样式
     */
    public static final float TYPE_SPAN_CHAR = 1.0f;                         //1.字符样式
                                                                             // {
    public static final float TYPE_SPAN_CHAR_BOLD = 1.01f;                   //1.01 粗体
    public static final float TYPE_SPAN_CHAR_ITALICS = 1.02f;                //1.02 斜体
    public static final float TYPE_SPAN_CHAR_UNDERLINE = 1.03f;              //1.03 下划线
    public static final float TYPE_SPAN_CHAR_MIDLINE = 1.04f;                //1.04 删除线
    public static final float TYPE_SPAN_CHAR_BACKGROUND = 1.05f;             //1.05 背景色
    public static final float TYPE_SPAN_CHAR_SIZE = 1.06f;                   //1.06 字号
    public static final float TYPE_SPAN_CHAR_COLOR = 1.07f;                  //1.07 字色
                                                                             // }

}
