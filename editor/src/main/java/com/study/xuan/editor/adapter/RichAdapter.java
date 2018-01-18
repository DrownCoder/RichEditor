package com.study.xuan.editor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.study.xuan.editor.R;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.paragraph.ParagraphHelper;
import com.study.xuan.editor.operate.span.MultiSpannableString;
import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static com.study.xuan.editor.common.Const.TYPE_EDIT;
import static com.study.xuan.editor.common.Const.TYPE_IMG;

/**
 * Author : xuan.
 * Date : 2017/11/3.
 * Description :input the description of this file.
 */

public class RichAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEADER = -1;
    public static final String DEFAULT_HINT = "文字输入区域";
    private final int STYLE_IMG_FOCUS = 1;
    private final int STYLE_IMG_NORMAL = 0;
    private HashSet<EditText> mEtHolder;
    private List<ImageHolder> mHolderShow;
    private List<RichModel> mData;
    private Context mContext;
    public int index = 0;

    private View mHeader;

    private ParagraphHelper paragraphHelper;

    public void addHeaderView(View header) {
        this.mHeader = header;
        notifyItemInserted(0);
    }

    public interface onScrollIndex {
        void scroll(int pos);
    }

    public interface onPhotoDelete {
        void onDelete(String path);
    }

    private onScrollIndex mOnScollIndex;
    private onPhotoDelete mOnPhotoDelete;

    public void setOnScollIndex(onScrollIndex mOnScollIndex) {
        this.mOnScollIndex = mOnScollIndex;
    }

    public void setOnPhotoDelete(onPhotoDelete onPhotoDelete) {
        this.mOnPhotoDelete = onPhotoDelete;
    }


    public RichAdapter(List<RichModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mEtHolder = new HashSet<>();
        mHolderShow = new LinkedList<>();
        paragraphHelper = new ParagraphHelper(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_EDIT:
                return new EditHolder(inflater.inflate(R.layout.item_edit, null, false));
            case TYPE_IMG:
                return new ImageHolder(inflater.inflate(R.layout.item_img, parent, false));
            case TYPE_HEADER:
                return new HeadHolder(mHeader);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_EDIT://编辑框
                bindEditComponent(holder, position - 1, mData.get(position - 1));
                break;
            case TYPE_IMG://图片
                bindImgComponent(holder, position - 1, mData.get(position - 1));
                break;
            case TYPE_HEADER://SingleText
                break;
        }
    }

    /**
     * 图片组件
     */
    private void bindImgComponent(RecyclerView.ViewHolder holder, int pos, RichModel item) {
        if (holder instanceof ImageHolder) {
            final ImageHolder imageHolder = (ImageHolder) holder;
            if (index == pos) {
                showImageBacSelected(imageHolder, STYLE_IMG_NORMAL);
            } else {
                showImageBacSelected(imageHolder, STYLE_IMG_FOCUS);
            }
            imageHolder.mIvDelete.setTag(pos);
            imageHolder.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearEditFocus();
                    index = (int) imageHolder.mIvDelete.getTag();
                    clearImgFocus(index);
                    showImageBacSelected(imageHolder, (Integer) v.getTag());
                }
            });
            imageHolder.mIvDelete.setOnClickListener(onClickListener);
        }
    }

    /**
     * 文本编辑器
     */
    private void bindEditComponent(RecyclerView.ViewHolder holder, final int pos, final
    RichModel item) {
        if (holder instanceof EditHolder) {
            final EditText mEdit = ((EditHolder) holder).mEt;
            mEtHolder.add(mEdit);
            if (index == pos) {
                mEdit.setFocusable(true);
                mEdit.setFocusableInTouchMode(true);
                mEdit.requestFocus();
            } else {
                mEdit.setFocusable(false);
            }
            ((EditHolder) holder).textWatcher.updatePosition(pos);
            ((EditHolder) holder).filter.updatePosition(pos);
            if (item.isParagraphStyle) {
                MultiSpannableString spannableString = new MultiSpannableString(item.source);
                spannableString.setMultiSpans(item.paragraphSpan.mSpans, 0, item.source.length(),
                        Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
                mEdit.setText(spannableString);
                paragraphHelper.handleTextStyle(mEdit, item.paragraphSpan.paragraphType);
            }else{
                mEdit.setText(item.source);
                paragraphHelper.handleTextStyle(mEdit, -1);
            }
            mEdit.setSelection(item.source.length());
            mEdit.setHint(item.hint);
            mEdit.setTag(pos);
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.iv_rich_delete) {
                int pos = (int) v.getTag();
                if (mOnPhotoDelete != null) {
                    mOnPhotoDelete.onDelete(mData.get(pos).source);
                }
                mData.remove(pos);
                index--;
                notifyDataChanged();

            } else if (i == R.id.et_rich_edit) {
                v.setFocusableInTouchMode(true);
                v.setFocusable(true);
                v.requestFocus();
                index = (int) v.getTag();
                clearImgFocus(index);
            }
        }
    };

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                switch (i) {
                    case KeyEvent.KEYCODE_DEL:
                        //删除
                        doDel(view, (Integer) view.getTag());
                        break;
                    case KeyEvent.KEYCODE_ENTER:
                        //回车
                        doEnter(view, (Integer) view.getTag());
                        return true;
                }
            }
            return false;
        }
    };

    /**
     * 是否显示图片被选中
     */
    private void showImageBacSelected(ImageHolder holder, int show) {
        switch (show) {
            case STYLE_IMG_FOCUS:
                mHolderShow.remove(holder);
                holder.mIvDelete.setVisibility(View.GONE);
                holder.mIv.clearFocus();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.mIv.setBackground(null);
                } else {
                    holder.mIv.setBackgroundDrawable(null);
                }
                holder.mIv.setTag(STYLE_IMG_NORMAL);
                break;
            case STYLE_IMG_NORMAL:
                mHolderShow.add(holder);
                holder.mIvDelete.setVisibility(View.VISIBLE);
                holder.mIv.requestFocus();
                ShapeBuilder.create()
                        .Stroke(5, Color.parseColor("#000000"))
                        .build(holder.mIv);
                holder.mIv.setTag(STYLE_IMG_FOCUS);
                break;
        }
    }


    /**
     * 清除所有选中的ImageView选中样式
     */
    private void clearImgFocus(int index) {
        for (int i = 0; i < mHolderShow.size(); i++) {
            ImageHolder holder = mHolderShow.get(i);
            int ci = (int) holder.mIvDelete.getTag();
            if (ci != index) {
                mHolderShow.remove(i);
                i--;
                holder.mIvDelete.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.mIv.setBackground(null);
                } else {
                    holder.mIv.setBackgroundDrawable(null);
                }
                holder.mIv.setTag(STYLE_IMG_NORMAL);
            }
        }
    }

    private void clearEditFocus() {
        for (EditText editText : mEtHolder) {
            editText.setFocusable(false);
        }
    }

    private void doEnter(View view, int pos) {
        this.index = pos;
        if (pos == 0 && mData.get(pos).type == TYPE_EDIT) {
            mData.get(pos).hint = "";
        }
        int cPos = ((EditText) view).getSelectionStart();
        RichModel item = mData.get(pos);
        if (cPos == ((EditText) view).getText().length()) {//光标在末尾
            mData.add(pos + 1, new RichModel(TYPE_EDIT, "", ""));
        } else {
            String newStr = item.source.substring(cPos, item.source.length());
            String oldStr = item.source.substring(0, cPos);
            item.setSource(oldStr);
            mData.add(pos + 1, new RichModel(TYPE_EDIT, newStr, ""));
        }
        index++;
        notifyDataChanged();
        if (mOnScollIndex != null) {
            mOnScollIndex.scroll(index + 1);//header需要+1
        }
    }

    private void doDel(View view, int pos) {
        if (((EditText) view).getSelectionStart() == 0) {
            if (pos >= 1) {
                if (mData.get(pos - 1).type == TYPE_EDIT) {
                    mData.get(pos - 1).append(mData.get(pos).source);
                    mData.remove(pos);
                    mEtHolder.remove(view);
                } else {
                    //KeyBoardUtils.hideSoftInputMethod(mContext);
                }
                index--;
                notifyDataChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return mData.get(position - 1).type;
    }

    public void notifyDataChanged() {
        if (mData.size() == 1 && mData.get(0).type == TYPE_EDIT) {
            mData.get(0).hint = DEFAULT_HINT;
        }
        notifyDataSetChanged();
    }

    private class EditHolder extends RecyclerView.ViewHolder {
        private CustomEditTextListener textWatcher;
        private EditText mEt;
        private CustomInputFilter filter;

        EditHolder(View itemView) {
            super(itemView);
            mEt = (EditText) itemView;
            mEt.setOnClickListener(onClickListener);
            textWatcher = new CustomEditTextListener();
            filter = new CustomInputFilter();
            mEt.addTextChangedListener(textWatcher);
            mEt.setOnKeyListener(onKeyListener);
            mEt.setFilters(new InputFilter[]{filter});
        }
    }

    private class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView mIv;
        private ImageView mIvDelete;

        ImageHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_rich_img);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_rich_delete);
        }
    }

    private class CustomEditTextListener implements TextWatcher {
        private int position;

        void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            mData.get(position).setSource(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }


    private class HeadHolder extends RecyclerView.ViewHolder {
        HeadHolder(View mHeader) {
            super(mHeader);
        }
    }

    private class CustomInputFilter implements InputFilter {
        private int position;
        private List<SpanModel> spanModels;
        private RichModel richModel;
        private MultiSpannableString spannableString;
        //当前span样式
        private SpanModel nowSpanModel;

        public CustomInputFilter() {
            spannableString = new MultiSpannableString();
        }

        void updatePosition(int position) {
            this.position = position;
            richModel = mData.get(position);
            spanModels = richModel.getSpanList();
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
            //输入后要做两步，1.保存新的样式2.设置新的样式
            Log.i("tag", "char:" + charSequence + "-" + start + "-" + end + "-" + dest + "-" +
                    dstart + "-" + dend);
            int i = 0;
            if (richModel.isNewSpan) {
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
                spannableString.setMultiSpans(nowSpanModel.mSpans, 0, charSequence.length(), Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableString;
        }
    }
}
