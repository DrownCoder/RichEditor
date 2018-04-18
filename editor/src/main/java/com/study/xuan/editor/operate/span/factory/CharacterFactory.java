package com.study.xuan.editor.operate.span.factory;

import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.operate.span.richspan.RichStyleSpan;
import com.study.xuan.editor.operate.span.richspan.URLSpanNoUnderline;
import com.study.xuan.editor.util.EditorUtil;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;

/**
 * Author : xuan.
 * Date : 2017/11/24.
 * Description :input the description of this file.
 */

public class CharacterFactory implements ICharacterStyleFactory {
    /**
     * code 类似于10101%932948%12
     * 用'%'分隔
     * 1.第一个表示粗体、斜体、下划线、中划线、背景色对应于前五位01表示false/true
     * 2.第二个表示字色
     * 3.第三个表示字号
     */

    @Override
    public List<CharacterStyle> createCharSpans(String code) {
        code = code.substring(1);
        String[] codes = code.split(Const.CODE_CHAR_SEPARATOR);
        List<CharacterStyle> characterStyles = new ArrayList<>();
        for(int i = 0; i < codes.length; i++) {
            switch (i) {
                case 0:
                    createFontStyleSpan(codes[i], characterStyles);
                    break;
                case 1:
                    createSizeSpan(codes[i], characterStyles);
                    break;
                case 2:
                    createColorSpan(codes[i], characterStyles);
                    break;
                case 3:
                    createUrlSpan(codes[i], characterStyles);
                    break;

            }
        }
        return characterStyles;
    }

    /**
     * 超链接无下划线
     */
    private void createUrlSpan(String code, List<CharacterStyle> characterStyles) {
        try {
            if (TextUtils.isEmpty(code)) return;
            URLSpanNoUnderline urlSpan = new URLSpanNoUnderline(code);
            characterStyles.add(urlSpan);
        } catch (NumberFormatException e) {
            Log.e(Const.BASE_LOG, "font size value is not true!");
        }
    }

    /**
     * 创建字号span
     */
    private void createSizeSpan(String code, List<CharacterStyle> characterStyles) {
        try {
            if (TextUtils.isEmpty(code) || Integer.parseInt(code) <= 0) return;
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(Integer.parseInt(code));
            characterStyles.add(sizeSpan);
        } catch (NumberFormatException e) {
            Log.e(Const.BASE_LOG, "font size value is not true!");
        }
    }

    /**
     * 创建字色span
     */
    private void createColorSpan(String code, List<CharacterStyle> characterStyles) {
        try {
            if (TextUtils.isEmpty(code) || Integer.parseInt(code) == 0) return;
            ForegroundColorSpan foregroundRightColor = new ForegroundColorSpan(Integer.parseInt
                    (code));
            characterStyles.add(foregroundRightColor);
        } catch (NumberFormatException e) {
            Log.e(Const.BASE_LOG, "color value is not true!");
        }
    }

    /**
     * 第一层，根据位置创建span
     */
    private void createFontStyleSpan(String code, List<CharacterStyle> characterStyles) {
        CharacterStyle cSpan;
        for (int i = 0;i<code.toCharArray().length;i++) {
            char c = code.toCharArray()[i];
            if (!EditorUtil.getCharBoolean(c)) {
                continue;
            }
            switch (i) {
                case 0://bold
                    cSpan = new RichStyleSpan(BOLD);
                    characterStyles.add(cSpan);
                    break;
                case 1://isItalics
                    cSpan = new RichStyleSpan(ITALIC);
                    characterStyles.add(cSpan);
                    break;
                case 2://isUnderLine
                    cSpan = new UnderlineSpan();
                    characterStyles.add(cSpan);
                    break;
                case 3://isCenterLine
                    cSpan = new StrikethroughSpan();
                    characterStyles.add(cSpan);
                    break;
                case 4://bacColor
                    break;
            }
        }
    }

}
