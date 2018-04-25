package com.study.xuan.editor.operate.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author : xuan.
 * Date : 2018/4/25.
 * Description :input the description of this file
 */

public class MarkDownTransformer implements Transformer {
    @Override
    public String paragraphRegex() {
        return "^(#|##|###|####|>)";
    }

    @Override
    public String imageRegex() {
        return "^\\!\\[.*?\\]\\(.*?\\)";
    }

    @Override
    public String fontRegex() {
        return "((\\*\\*(.+)\\*\\*)|(\\*(.+)\\*)|(\\~\\~(.+)\\~\\~))";
    }

    @Override
    public String imageUrl(String str) {
        String regex = "^\\!\\[(.*?)\\]\\((.*?)\\)";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        String name = matcher.group(1);
        String url = matcher.group(2);
        return url;
    }

    @Override
    public String boldRegex() {
        return "^(\\*\\*(.+)\\*\\*)";
    }

    @Override
    public String italicsRegex() {
        return "^(\\*(.+)\\*)";
    }

    @Override
    public String centerRegex() {
        return "^(\\~\\~(.+)\\~\\~)";
    }
}
