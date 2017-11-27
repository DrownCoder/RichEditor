package com.study.xuan.editor.widget.span.factory;

import android.text.style.CharacterStyle;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

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

public class CharactorFactory implements ICharacterStyleFactory {
    @Override
    public List<CharacterStyle> createCharSpans(String code) {
        code = code.substring(1);
        List<CharacterStyle> characterStyles = new ArrayList<>();
        for (int i = 0;i<code.toCharArray().length;i++) {
            char c = code.toCharArray()[i];
            if (!EditorUtil.getCharBoolean(c)) {
                continue;
            }
            switch (i) {
                case 0://bold
                    StyleSpan bold = new StyleSpan(BOLD);
                    characterStyles.add(bold);
                    break;
                case 1://isItalics
                    StyleSpan italics = new StyleSpan(ITALIC);
                    characterStyles.add(italics);
                    break;
                case 2://isUnderLine
                    UnderlineSpan underline = new UnderlineSpan();
                    characterStyles.add(underline);
                    break;
                case 3://isCenterLine
                    StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                    characterStyles.add(strikethroughSpan);
                    break;
                case 4:
                    break;
            }
        }
        return characterStyles;
    }

}
