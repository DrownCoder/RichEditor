package com.study.xuan.editor.operate.search;

import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.font.FontParam;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-3-24.
 * Description : 常规遍历
 */
public class NormalSearch implements ISearchStrategy {
    @Override
    public FontParam indexParam(List<SpanModel> data, int index) {
        int pos = indexPost(data, index);
        return pos == -1 ? null : data.get(pos).param;
    }

    @Override
    public int indexPost(List<SpanModel> data, int index) {
        SpanModel model;
        for (int i = 0; i < data.size(); i++) {
            model = data.get(i);
            if (index == 0 && index == model.start) {
                return i;
            }
            if (index > model.end || index < model.start) {
                continue;
            }
            if (index > model.start && index <= model.end) {
                return i;
            }
        }
        return -1;
    }
}
