package com.study.xuan.editor.operate.paragraph;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.common.DPVALUE;

/**
 * Author : xuan.
 * Date : 2018/1/17.
 * Description :段落帮助类，将Edittext改变成段落对应的样式
 */

public class ParagraphHelper {
    private Context mContext;
    private DPVALUE DPValue;
    private RecyclerView.LayoutParams mParams;

    public ParagraphHelper(Context mContext) {
        this.mContext = mContext;
        init();
    }

    private void init() {
        DPValue = new DPVALUE(mContext);
    }

    public void handleTextStyle(EditText tv, int type) {
        switch (type) {
            case Const.PARAGRAPH_REFER:
                tv.setBackgroundColor(Color.parseColor("#f7f7f7"));
                mParams = (RecyclerView.LayoutParams) tv.getLayoutParams();
                if (mParams != null) {
                    mParams.setMargins(DPValue.DP_10, DPValue.DP_12, DPValue.DP_10, DPValue.DP_12);
                }
                tv.setPadding(0, 0, 0, 0);
                break;
            default:
                tv.setBackgroundColor(0);
                mParams = (RecyclerView.LayoutParams) tv.getLayoutParams();
                if (mParams != null) {
                    mParams.setMargins(0, 0, 0, 0);
                }
                tv.setPadding(0, DPValue.DP_12, 0, DPValue.DP_12);
        }

    }
}
