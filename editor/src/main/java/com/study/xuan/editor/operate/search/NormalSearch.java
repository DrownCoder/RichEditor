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
        for (SpanModel model : data) {
            if (index == 0 && index == model.start) {
                return model.param;
            }
            if (index > model.end) {
                continue;
            }
            if (index > model.start && index <= model.end) {
                return model.param;
            } else if (index < model.start) {
                return null;
            }
        }
        return null;
    }
}
