package com.study.xuan.editor.operate.span.factory;

import android.text.style.CharacterStyle;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/24.
 * Description :input the description of this file.
 */

public interface ICharacterStyleFactory {
    public List<CharacterStyle> createCharSpans(String code);
}
