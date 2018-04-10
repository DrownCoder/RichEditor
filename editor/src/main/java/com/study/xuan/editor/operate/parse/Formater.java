package com.study.xuan.editor.operate.parse;

/**
 * Author : xuan.
 * Date : 18-4-10.
 * Description : the file description
 */
public interface Formater {
    String formatBold(String str);

    String formatItalics(String str);

    String formatCenterLine(String str);

    String formatUnderLine(String str);

    String formatLink(String name, String str);

    String formatH1(String h1);

    String formatH2(String h2);

    String formatH3(String h3);

    String formatH4(String h4);
}
