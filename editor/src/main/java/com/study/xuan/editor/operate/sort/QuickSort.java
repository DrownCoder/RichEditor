package com.study.xuan.editor.operate.sort;

import com.study.xuan.editor.model.SpanModel;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-11.
 * Description :
 */
public class QuickSort implements ISortStrategy<SpanModel> {
    @Override
    public void sort(List<SpanModel> data, int start, int end) {
        if (data == null || data.size() <= 1) {
            return;
        }
        //如果不止一个元素，继续划分两边递归排序下去
        int partition = divide(data, start, data.size() - 1);
        sort(data, start, partition - 1);
        sort(data, partition + 1, end);
    }

    public int divide(List<SpanModel> data, int start, int end) {
        //每次都以最右边的元素作为基准值
        int base = data.get(end).start;
        //start一旦等于end，就说明左右两个指针合并到了同一位置，可以结束此轮循环。
        while (start < end) {
            while (start < end && data.get(start).start <= base)
                //从左边开始遍历，如果比基准值小，就继续向右走
                start++;
            //上面的while循环结束时，就说明当前的a[start]的值比基准值大，应与基准值进行交换
            if (start < end) {
                //交换
                SpanModel temp = data.get(start);
                data.add(start, data.remove(end));
                data.add(temp);
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值右边)，因此右边也要同时向前移动一位
                end--;
            }
            while (start < end && data.get(end).start >= base)
                //从右边开始遍历，如果比基准值大，就继续向左走
                end--;
            //上面的while循环结束时，就说明当前的a[end]的值比基准值小，应与基准值进行交换
            if (start < end) {
                SpanModel temp = data.get(start);
                data.add(start, data.remove(end));
                data.add(temp);
                //交换后，此时的那个被调换的值也同时调到了正确的位置(基准值左边)，因此左边也要同时向后移动一位
                start++;
            }

        }
        //这里返回start或者end皆可，此时的start和end都为基准值所在的位置
        return end;
    }
}
