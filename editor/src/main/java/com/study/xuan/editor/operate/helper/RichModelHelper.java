package com.study.xuan.editor.operate.helper;

import com.study.xuan.editor.model.SpanModel;

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
    public static void  formatSpan(List<SpanModel> oldSpans) {
        //todo 先用快速排序将区间排序规整，再合并相临
        if (oldSpans == null) {
            return ;
        }
        for (int i = 0; i < oldSpans.size() - 1; i++) {
            List<Object> oldObj = oldSpans.get(i).mSpans;
            List<Object> nextObj = null;
            if (i + 1 < oldSpans.size()) {
                nextObj = oldSpans.get(i + 1).mSpans;
            }else{
                break;
            }
            while (checkSpanIsSame(oldObj, nextObj)) {
                oldSpans.get(i).end = oldSpans.get(i + 1).end;
                oldSpans.remove(i + 1);
                if (i + 1 < oldSpans.size()) {
                    nextObj = oldSpans.get(i + 1).mSpans;
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
            if (!obj1.get(i).getClass().getName().equals(obj2.get(i).getClass().getName())) {
                return false;
            }
        }
        return true;
    }
}
