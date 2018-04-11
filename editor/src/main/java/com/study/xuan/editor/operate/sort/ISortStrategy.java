package com.study.xuan.editor.operate.sort;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-11.
 * Description : 排序算法
 */
public interface ISortStrategy<T> {
    void sort(List<T> data, int start, int end);
}
