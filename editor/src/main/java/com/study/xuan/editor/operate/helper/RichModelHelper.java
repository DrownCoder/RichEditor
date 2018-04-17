package com.study.xuan.editor.operate.helper;

import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.sort.QuickSort;
import com.study.xuan.editor.operate.span.RichStyleSpan;

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
}
