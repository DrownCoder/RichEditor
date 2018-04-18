package com.study.xuan.editor.operate.helper;

import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.RichBuilder;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.span.richspan.RichStyleSpan;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-10.
 * Description : the file description
 */
public class RichModelHelper {
    /**
     * 整合Span，相邻的两个span样式相同，则合并
     */
    public static void formatSpan(List<SpanModel> oldSpans) {
        //todo 先用快速排序将区间排序规整，再合并相临
        if (oldSpans == null) {
            return;
        }
        //QuickSort sortStrategy = new QuickSort();
        // sortStrategy.sort(oldSpans, 0, oldSpans.size());


        for (int i = 0; i < oldSpans.size() - 1; i++) {
            if (oldSpans.get(i).end == oldSpans.get(i + 1).start) {
                //前后两个相邻
                List<Object> oldObj = oldSpans.get(i).mSpans;
                List<Object> nextObj;
                nextObj = oldSpans.get(i + 1).mSpans;
                if (checkSpanIsSame(oldObj, nextObj)) {
                    oldSpans.get(i).end = oldSpans.get(i + 1).end;
                    oldSpans.remove(i + 1);
                    i--;
                }
            }
        }
    }

    private static boolean checkSpanIsSame(List<Object> obj1, List<Object> obj2) {
        if (obj1 == null) {
            if (obj2 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (obj2 == null) {
                return false;
            }
        }
        //都不为null
        if (obj1.size() != obj2.size()) {
            return false;
        }
        for (int i = 0; i < obj1.size(); i++) {
            if (obj1.get(i) instanceof RichStyleSpan && obj2.get(i) instanceof RichStyleSpan) {
                RichStyleSpan span1 = (RichStyleSpan) obj1.get(i);
                RichStyleSpan span2 = (RichStyleSpan) obj2.get(i);
                if (span1.equals(span2)) {
                    return true;
                }
                return false;
            }
            if (!obj1.get(i).getClass().getName().equals(obj2.get(i).getClass().getName())) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断两个Param是否相同
     */
    public static boolean isParamSimilar(FontParam param1, FontParam param2) {
        if (param1 == null) {
            if (param2 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (param2 == null) {
                return false;
            }
        }
        return param1.toString().equals(param2.toString());
    }

    /**
     * 在start-end区间内的样式移除并分割
     */
    public static int cleanBetweenArea(List<SpanModel> data, int start, int end) {
        if (data == null || start == end) {
            return 0;
        }
        SpanModel model;
        for (int i = 0; i < data.size(); i++) {
            model = data.get(i);
            if (model.end <= start) {
                continue;
            }
            if (model.start >= end) {
                continue;
            }
            if (model.start == start && model.end == end) {
                data.remove(model);
                return i;
            }
            if (model.start > start && model.end < end) {
                data.remove(model);
                continue;
            }
            if (model.start <= start && start < model.end) {
                if (end < model.end) {
                    //区间在大区间内
                    SpanModel newSpan = cloneParam(model, end, model.end);
                    model.end = start;
                    data.add(i + 1, newSpan);
                    continue;
                } else {
                    model.end = start;
                }
            }
            if (model.start > end && model.end >= end) {
                model.start = end;
            }
            if (model.start == model.end) {
                data.remove(model);
            }
        }
        return data.size();
    }

    public static SpanModel cloneParam(SpanModel oldSpan, int start, int end) {
        SpanModel span = new SpanModel(oldSpan.param);
        span.mSpans.addAll(RichBuilder.getInstance().getFactory().createSpan(span.param.getCharCodes()));
        span.start = start;
        span.end = end;
        return span;
    }
}
