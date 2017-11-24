package com.study.xuan.editor.widget.span.factory;

import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/24.
 * Description :input the description of this file.
 */

public interface IUpdateAppearanceFactory {
    public List<UpdateAppearance> createUpdateSpans(String code);
}
