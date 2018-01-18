package com.study.xuan.editor.operate;

import com.study.xuan.editor.operate.font.FontParam;

/**
 * Author : xuan.
 * Date : 2018/1/18.
 * Description :input the description of this file.
 */

public interface IParamManger {
    public boolean needNewSpan(FontParam param);

    public FontParam createNewParam();

    public String getParamCode(int pType);
}
