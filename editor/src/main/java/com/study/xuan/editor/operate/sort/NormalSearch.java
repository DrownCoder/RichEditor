package com.study.xuan.editor.operate.sort;

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
        SearchResult result = indexPost(data, index);
        return result.resultCode < 0 ? null : data.get(result.resultIndex).param;
    }

    @Override
    public SearchResult indexPost(List<SpanModel> data, int index) {
        SpanModel model;
        for (int i = 0; i < data.size(); i++) {
            model = data.get(i);
            if (index == 0 && index == model.start) {
                return new SearchResult(i);
            }
            if (index == model.start || index == model.end) {
                //两个区间之间
                return new SearchResult(SearchResult.CODE_BETWEEN, i);
            }
            if (index > model.end || index < model.start) {
                continue;
            }
            if (index > model.start && index < model.end) {
                return new SearchResult(i);
            }
        }
        return new SearchResult();
    }

    @Override
    public SearchResult indexPost(List<SpanModel> data, int index,boolean isStart) {
        SpanModel model;
        for (int i = 0; i < data.size(); i++) {
            model = data.get(i);
            if (index == 0 && index == model.start) {
                return new SearchResult(i);
            }
            //两个区间之间,算作前置区间，还是后置区间
            if (isStart) {
                if (index == model.start) {
                    return new SearchResult(SearchResult.CODE_BETWEEN, i);
                }
            }else{
                if (index == model.end) {
                    return new SearchResult(SearchResult.CODE_BETWEEN, i);
                }
            }
            if (index > model.end || index < model.start) {
                continue;
            }
            if (index > model.start && index < model.end) {
                return new SearchResult(i);
            }
        }
        return new SearchResult();
    }
}
