package com.study.xuan.editor.common;

/**
 * Author : xuan.
 * Date : 2017/11/17.
 * Description :全局类型
 */

public class Const {
    public static final String BASE_LOG = "RichEditor";                     //Log信息
    /**
     * span标志
     */
    public static final String SPAN_TYPE_FONT = "1";                             //字符样式
    public static final String SPAN_TYPE_PARAGRAPH = "2";                        //段落样式

    /**
     * 字符样式
     */
    public static final int PANEL_FONT_STYLE = 0;                           //字体样式
    public static final int PANEL_FONT_SIZE = 1;                            //字体大小
    public static final int PANEL_FONT_COLOR = 2;                           //字体颜色
    public static final int PANEL_HEADER = 3;                               //标题样式

    /**
     * 段落类型
     */
    public static final int PARAGRAPH_NONE = -1;
    public static final int PARAGRAPH_REFER = 0;                            //引用类型
    public static final int PARAGRAPH_T1 = 1;                               //标题1
    public static final int PARAGRAPH_T2 = 2;                               //标题2
    public static final int PARAGRAPH_T3 = 3;                               //标题3
    public static final int PARAGRAPH_T4 = 4;                               //标题4

    /**
     * 段落数值
     */
    public static final int T1_SIZE = 32;                                   //标题1字号
    public static final int T2_SIZE = 28;                                   //标题2字号
    public static final int T3_SIZE = 24;                                   //标题3字号
    public static final int T4_SIZE = 20;                                   //标题4字号

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
     * 默认文字大小
     */
    public static final int DEFAULT_TEXT_SIZE = 18;
    /**
     * 默认超链接颜色
     */
    public static final String DEFAULT_LINK_COLOR = "#3194D0";

    /**
     * 解析类型
     */
    public static final int GSON_PARSE_TYPE = 0;
    public static final int MARKDOWN_PARSE_TYPE = 1;
    public static final int HTML_PARSE_TYPE = 2;

    /**
     * 数据库
     */
    public static final String DB_NAME = "RichNote.db";
    public static final int DB_VERSION = 1;

}
