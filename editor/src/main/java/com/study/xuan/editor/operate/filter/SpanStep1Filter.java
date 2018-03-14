package com.study.xuan.editor.operate.filter;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.util.Log;

import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.span.MultiSpannableString;

import java.util.List;

import static com.study.xuan.editor.common.Const.BASE_LOG;

/**
 * Author : xuan.
 * Date : 2017/3/3.
 * Description :InputFilter过滤器，第一步过滤：主要用于设置span
 */
public class SpanStep1Filter implements InputFilter, ISpanFilter {
    private List<SpanModel> spanModels;
    private RichModel richModel;
    private MultiSpannableString spannableString;
    private boolean isNotify;
    private List<RichModel> mData;

    public SpanStep1Filter(List<RichModel> data) {
        spannableString = new MultiSpannableString();
        mData = data;
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
                //notify后重置为SPAN_EXCLUSIVE_EXCLUSIVE，后面的会受前面的影响
                spannableString.setMultiSpans(model.mSpans, model.start, model.end, Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableString;
        }
        if (isDelete) {
            //sortOnDelete(dstart, dend);
            //sortAfterDelete(dstart, dend);
            return charSequence;
        }
        if (richModel.newSpan.mSpans.size() == 0) {
            return charSequence;
        }
        spannableString.clear();
        spannableString.clearSpans();
        spannableString.append(charSequence);
        if (richModel.newSpan != null) {
            //logModel();
            //设置为SPAN_EXCLUSIVE_INCLUSIVE，后面的会受前面的影响
            spannableString.setMultiSpans(richModel.newSpan.mSpans, 0, charSequence.length(), Spanned
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
    @Deprecated
    private void sortOnDelete(int start, int end) {
        //todo 1.更改范围受影响的区间，2.后面未受影响的区间前移
        int size = end - start;
        for (int i = 0; i < spanModels.size(); i++) {
            SpanModel model = spanModels.get(i);
            //找到当前光标所属的区间范围，前取后不取，类似[0,3)，[3,5)
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


    /**
     * 删除，重新排列数据算法
     * 1)首先判断删除区间和当前区间是否有交集，无交集执行2），有交集执行3）
     * 2)当无交集，则取下一个区间，继续执行1）
     * 2)当有交集，判断是否删除区间是否是当前区间的子集，是子集执行3），不是子集执行4）
     * 3)当删除区间和当前区间是子集关系，当前区间的end减去删除区间的长度即可，并执行n)判断
     * 4)当删除区间和当前区间不是子集关系，当前区间的
     */
    private void sortAfterDelete(int start, int end) {
        final int fsize = end - start;
        int size = fsize;
        for (int i = 0; i < spanModels.size(); i++) {
            SpanModel model = spanModels.get(i);
            //找到当前光标所属的区间范围，前取后不取，类似[0,3)，[3,5)
            if (start >= model.start && start < model.end) {
                //删除区域跨范围，[0,3)，[3,5),[5,6),删除3-5，则移除中间
                while (i < spanModels.size()) {
                    if (model.start >= end || model.end <= start) {
                        //删除区间和当前区间无交集
                        break;
                    } else {
                        //删除区间和当前区间有交集
                        if (model.start <= start && model.end >= end) {
                            //删除区间是当前区间的子集
                            model.end -= fsize;
                        } else {
                            //删除区间跨区间
                            if (end <= model.end) {
                                //前删法，后面前移
                                int modesize = model.end - model.start;
                                model.start -= fsize - size;
                                model.end = model.start + (modesize - size + 1);
                            } else {
                                //尾删法，前面不动
                                model.end -= (model.end - start);
                                size -= (model.end - start + 1);
                            }
                        }

                        if (model.start == model.end) {
                            spanModels.remove(i);
                            if (i < spanModels.size()) {
                                model = spanModels.get(i);
                            }
                        } else {
                            i++;
                            if (i < spanModels.size()) {
                                model = spanModels.get(i);
                            }
                        }
                    }
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

    @Override
    public void updatePosition(int position) {
        richModel = mData.get(position);
        spanModels = richModel.getSpanList();
        isNotify = true;
    }
}