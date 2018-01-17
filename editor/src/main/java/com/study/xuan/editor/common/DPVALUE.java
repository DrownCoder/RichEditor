package com.study.xuan.editor.common;

import android.content.Context;

import com.study.xuan.editor.util.DensityUtil;

/**
 * Author : xuan.
 * Date : 2018/1/17.
 * Description :input the description of this file.
 */

public class DPVALUE {
    public int DP_12;
    public int DP_10;

    public DPVALUE(Context context) {
        DP_12 = DensityUtil.dp2px(context, 12);
        DP_10 = DensityUtil.dp2px(context, 10);
    }
}
