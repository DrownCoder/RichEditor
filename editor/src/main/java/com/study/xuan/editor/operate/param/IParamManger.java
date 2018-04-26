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

    FontParam cloneParam(FontParam param);

    String getParamCode(int pType);

    String getParamCode(FontParam param, int pType);

    void setCurrentParam(FontParam param);

    IParamManger reset();
}
