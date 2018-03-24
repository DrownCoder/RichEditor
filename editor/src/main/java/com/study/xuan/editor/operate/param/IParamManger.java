package com.study.xuan.editor.operate.param;

import com.study.xuan.editor.operate.font.FontParam;

/**
 * Author : xuan.
 * Date : 2018/1/18.
 * Description :input the description of this file.
 */

public interface IParamManger {
    boolean needNewSpan(FontParam param);

    FontParam createNewParam();

    String getParamCode(int pType);
}
