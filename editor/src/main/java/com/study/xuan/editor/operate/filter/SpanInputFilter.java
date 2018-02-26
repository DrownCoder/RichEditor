package com.study.xuan.editor.operate.filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.span.MultiSpannableString;

import java.util.List;

import static com.study.xuan.editor.common.Const.BASE_LOG;

public class SpanInputFilter implements InputFilter {
    private List<SpanModel> spanModels;
    private RichModel richModel;
    private MultiSpannableString spannableString;
    //当前span样式
    private SpanModel nowSpanModel;
    private boolean isNotify;
    private List<RichModel> mData;

    public SpanInputFilter(List<RichModel> data) {
        spannableString = new MultiSpannableString();
        mData = data;
    }

    public void updatePosition(int position) {
        richModel = mData.get(position);
        spanModels = richModel.getSpanList();
        isNotify = true;
    }

    /**
     * @param charSequence 即将输入的字符串
     * @param start        source的start
     * @param end          source的end start为0，end也可理解为source长度
     * @param dest         dest输入框中原来的内容
     * @param dstart       要替换或者添加的起始位置，即光标所在的位置
     * @param dend         要替换或者添加的终止始位置，若为选择一串字符串进行更改，则为选中字符串 最后一个字符在dest中的位置
     */
    @Override
    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int
            dstart, int dend) {
        Log.i(BASE_LOG, "char:" + charSequence + "-" + start + "-" + end + "-" + dest + "-" +
                dstart + "-" + dend);
        //判断是否是删除
        boolean isDelete = TextUtils.isEmpty(charSequence);
        if (isNotify) {
            isNotify = false;
            spannableString.clear();
            spannableString.clearSpans();
            spannableString.append(charSequence);
            Log.i(BASE_LOG, spannableString.toString());
            for (SpanModel model : spanModels) {
                Log.i(BASE_LOG, model.mSpans + "start:" + model.start + "end:" + model.end);
                spannableString.setMultiSpans(model.mSpans, model.start, model.end, Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableString;
        }
        if (isDelete) {
            sortOnDelete(dstart, dend);
            return charSequence;
        }
        //输入后要做两步，1.保存新的样式2.设置新的样式
        int i = 0;
        if (richModel.isNewSpan) {
            //使用过后，变成false
            richModel.isNewSpan = false;
            //新建span样式
            nowSpanModel = richModel.newSpan;
            if (dstart == richModel.source.length()) {
                //光标在文段末尾
                nowSpanModel.start = dstart;
                nowSpanModel.end = dstart + end - start;
                spanModels.add(nowSpanModel);
            } else {
                //光标在文中
                for (; i < spanModels.size(); i++) {
                    SpanModel spanModel = spanModels.get(i);
                    if (start > spanModel.end) {
                        continue;
                    }
                    if (start > spanModel.start && start < spanModel.end) {
                        //光标位置类似：“这是一段文字|光标在中间”
                        spanModel.end = start;
                        nowSpanModel.start = start;
                        nowSpanModel.end = start + end - start;
                        spanModels.add(i, nowSpanModel);
                        break;
                    }

                }
            }
        } else {
            //保持原有span样式，不进行插入操作
            for (; i < spanModels.size(); i++) {
                SpanModel spanModel = spanModels.get(i);
                if (dstart > spanModel.end) {
                    //光标在一段文字末尾修改，则到下一个span
                    continue;
                }
                if (dstart > spanModel.start && dstart <= spanModel.end) {
                    //光标位置类似：“这是一段文字|光标在中间”
                    nowSpanModel = spanModel;
                    spanModel.end += end - start;
                    i++;
                    break;
                }
            }
            for (; i < spanModels.size(); i++) {
                spanModels.get(i).start += end - start;
                spanModels.get(i).end += end - start;
            }
        }
        spannableString.clear();
        spannableString.clearSpans();
        spannableString.append(charSequence);
        if (nowSpanModel != null) {
            //logModel();
            spannableString.setMultiSpans(nowSpanModel.mSpans, 0, charSequence.length(), Spanned
                    .SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 删除，重新排列数据算法
     * [0,3)[3,5)
     * 1)从末尾删除,5开始删
     * 2)从中间删除,3开始删
     * 3)从一个范围中开始删,从4开始删
     */
    private void sortOnDelete(int start, int end) {
        //todo 1.更改范围受影响的区间，2.后面未受影响的区间前移
        int size = end - start;
        for (int i = 0; i < spanModels.size(); i++) {
            SpanModel model = spanModels.get(i);
            //找到当前光标所属的坐标范围，前取后不取，类似[0,3)，[3,5)
            if (start >= model.start && start < model.end) {
                //删除区域跨范围，[0,3)，[3,5),[5,6),删除3-5，则移除中间
                while (i < spanModels.size() - 1
                        && start <= model.start && end > model.end) {
                    spanModels.remove(i + 1);
                    model = spanModels.get(i + 1);
                }
                //删除后，后面范围未受影响的，前移size
                for (; i < spanModels.size(); i++) {
                    SpanModel item = spanModels.get(i);
                    if (item.start > start) {
                        item.start -= size;
                    }
                    item.end -= size;
                    if (item.start == item.end) {
                        spanModels.remove(i);
                    }
                }
                logModel();
                return;
            }
        }
    }

    private void logModel() {
        for (SpanModel item : spanModels) {
            Log.i(BASE_LOG, item.mSpans + "start:" + item.start + "end:" + item.end);
        }
    }
}