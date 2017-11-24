package com.study.xuan.editor.operate;

/**
 * Author : xuan.
 * Date : 2017/11/22.
 * Description :Param管理类
 */

public class FontParamManager {
    private FontParam curParams;
    private FontParam newParams;

    /**
     * 判断是否需要构造新的span
     */
    public boolean needNewSpan(FontParam param) {
        if (curParams == null) {
            this.newParams = param;
            return true;
        }
        if (curParams.toString().equals(param.toString())) {
            return false;
        }else{
            this.newParams = param;
            return true;
        }
    }

    /**
     * 返回新的参数，利用clone复制
     */
    public FontParam createNewParam(){
        try {
            curParams = (FontParam) newParams.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return curParams;
    }
}
