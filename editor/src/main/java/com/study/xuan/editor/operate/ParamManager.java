package com.study.xuan.editor.operate;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.font.FontParamBuilder;

/**
 * Author : xuan.
 * Date : 2017/11/22.
 * Description :Param管理类
 */

public class ParamManager implements IParamManger {
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

    @Override
    public String getParamCode(int pType) {
        switch (pType) {
            case Const.PARAGRAPH_REFER:
                return Const.CODE_FONT_SEPARATOR + Const.SPAN_TYPE_PARAGRAPH
                        + pType;
            case Const.PARAGRAPH_T1:
                return new FontParamBuilder().fontSize(36).build().getCharCodes()
                        + Const.CODE_FONT_SEPARATOR + Const.SPAN_TYPE_PARAGRAPH
                        + pType;
            case Const.PARAGRAPH_T2:
                return new FontParamBuilder().fontSize(32).build().getCharCodes()
                        + Const.CODE_FONT_SEPARATOR + Const.SPAN_TYPE_PARAGRAPH
                        + pType;
            case Const.PARAGRAPH_T3:
                return new FontParamBuilder().fontSize(26).build().getCharCodes()
                        + Const.CODE_FONT_SEPARATOR + Const.SPAN_TYPE_PARAGRAPH
                        + pType;
            case Const.PARAGRAPH_T4:
                return new FontParamBuilder().fontSize(22).build().getCharCodes()
                        + Const.CODE_FONT_SEPARATOR + Const.SPAN_TYPE_PARAGRAPH
                        + pType;
        }
        return curParams.getCharCodes();
    }

    public void reset() {
        if (curParams != null) {
            curParams.reset();
        }
        if (newParams != null) {
            newParams.reset();
        }
    }
}
