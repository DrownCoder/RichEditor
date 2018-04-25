package com.study.xuan.editor.operate.parse;

/**
 * Author : xuan.
 * Date : 2018/4/25.
 * Description :input the description of this file
 */

public interface Transformer {
    String paragraphRegex();

    String imageRegex();

    String fontRegex();

    String imageUrl(String str);

    String boldRegex();

    String italicsRegex();

    String centerRegex();
}
