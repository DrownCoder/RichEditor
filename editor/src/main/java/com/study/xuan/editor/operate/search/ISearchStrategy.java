package com.study.xuan.editor.operate.search;

import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.font.FontParam;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-3-24.
 * Description : 定位当前光标所在的区间的样式，后期可以考虑使用二分法替代遍历
 */
public interface ISearchStrategy {
    FontParam indexParam(List<SpanModel> data, int index);

    int indexPost(List<SpanModel> data, int index);
}

