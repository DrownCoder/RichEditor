package com.study.xuan.editor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.xuan.editor.R;
import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.helper.RichModelHelper;
import com.study.xuan.editor.operate.param.IParamManger;
import com.study.xuan.editor.operate.filter.SpanStep1Filter;
import com.study.xuan.editor.operate.filter.SpanStep2Filter;
import com.study.xuan.editor.operate.RichBuilder;
import com.study.xuan.editor.operate.paragraph.ParagraphHelper;
import com.study.xuan.editor.operate.span.richspan.MultiSpannableString;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.util.DensityUtil;
import com.study.xuan.editor.widget.RichEditText;
import com.study.xuan.library.widget.EasyTextView;
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
    public static final int TYPE_HEADER = -1;
    public static final String DEFAULT_HINT = "文字输入区域";
    private final int STYLE_IMG_FOCUS = 1;
    private final int STYLE_IMG_NORMAL = 0;
    private HashSet<EditText> mEtHolder;
    private List<ImageHolder> mHolderShow;
    private List<RichModel> mData;
    private Context mContext;
    public int index = 0;
    public EditText mCurEdit;
    private MultiSpannableString mSpanString;

    private View mHeader;

    private ParagraphHelper paragraphHelper;
    private IAbstractSpanFactory factory;
    private IParamManger paramManger;

    public boolean isEnter;
    private boolean hasHeader;

    public void addHeaderView(View header) {
        this.mHeader = header;
        if (mHeader != null) {
            hasHeader = true;
        }
        notifyItemInserted(0);
    }

    public interface onScrollIndex {
        void scroll(int pos);
    }

    public interface onPhotoDelete {
        void onDelete(String path);
    }

    public interface onEditEvent {
        //pos对应光标所在行数，index对应光标所在位置
        void onEditClick(int pos, int index);

        void onEditSelect(int pos, int start, int end);

        void onChange();
    }

    private onScrollIndex mOnScrollIndex;
    private onPhotoDelete mOnPhotoDelete;
    private onEditEvent mOnEditEvent;

    public void setOnScrollIndex(onScrollIndex mOnScrollIndex) {
        this.mOnScrollIndex = mOnScrollIndex;
    }

    public void setOnPhotoDelete(onPhotoDelete onPhotoDelete) {
        this.mOnPhotoDelete = onPhotoDelete;
    }

    public void setOnEditClick(onEditEvent mOnEditEvent) {
        this.mOnEditEvent = mOnEditEvent;
    }

    public RichAdapter(List<RichModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mEtHolder = new HashSet<>();
        mHolderShow = new LinkedList<>();
        paragraphHelper = new ParagraphHelper(mContext);
        mSpanString = new MultiSpannableString();
        factory = RichBuilder.getInstance().getFactory();
        paramManger = RichBuilder.getInstance().getManger();
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
        position = hasHeader ? (position - 1) : position;
        switch (getItemViewType(position)) {
            case TYPE_EDIT://编辑框
                bindEditComponent(holder, position, mData.get(position));
                break;
            case TYPE_IMG://图片
                bindImgComponent(holder, position, mData.get(position));
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
            Glide.with(mContext).load(item.source).into(imageHolder.mIv);
            imageHolder.mIvDelete.setTag(pos);
            imageHolder.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearEditFocus();
                    index = (int) imageHolder.mIvDelete.getTag();
                    clearImgFocus(index);
                    showImageBacSelected(imageHolder, (Integer) v.getTag(Integer.MAX_VALUE));
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
                mCurEdit = ((EditHolder) holder).mEt;
                mEdit.setFocusable(true);
                mEdit.setFocusableInTouchMode(true);
                mEdit.requestFocus();
            } else {
                mEdit.setFocusable(false);
            }
            ((EditHolder) holder).textWatcher.updatePosition(pos);
            ((EditHolder) holder).filter.updatePosition(pos);
            mEdit.setTextSize(Const.DEFAULT_TEXT_SIZE);
            if (item.isParagraphStyle) {
                SpannableStringBuilder spannableString = new SpannableStringBuilder(item.source);
                for (Object obj : item.paragraphSpan.mSpans) {
                    if (obj instanceof AbsoluteSizeSpan) {
                        AbsoluteSizeSpan sizeSpan = (AbsoluteSizeSpan) obj;
                        mEdit.setTextSize(sizeSpan.getSize());
                        continue;
                    }
                    spannableString.setSpan(obj, 0, item.source.length(), Spanned
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                mEdit.setText(spannableString);
                paragraphHelper.handleTextStyle(mEdit, item.paragraphSpan.paragraphType);
            } else {
                mSpanString.clear();
                mSpanString.clearSpans();
                mSpanString.append(item.source);
                if (isEnter) {
                    for (SpanModel span : item.getSpanList()) {
                        mSpanString.setMultiSpans(span.mSpans, span.start, span.end, Spanned
                                .SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                mEdit.setText(mSpanString);
                paragraphHelper.handleTextStyle(mEdit, -1);
            }
            mEdit.setSelection(item.curIndex);
            //mEdit.setSelection(item.source.length());
            mEdit.setHint(item.hint);
            mEdit.setTag(pos);
            //只存在一个EditText的时候，点击区域太小，所以只有一个的时候，将MinHeight设为500
            if (index == pos && index < 2 && index == mData.size() - 1) {
                mEdit.setMinHeight(DensityUtil.dp2px(mContext, 500));
            } else {
                mEdit.setMinHeight(DensityUtil.dp2px(mContext, 0));
            }
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.etv_rich_delete) {
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
                mData.get(index).curIndex = ((EditText) v).getSelectionStart();
                if (mOnEditEvent != null) {
                    mOnEditEvent.onEditClick(index, ((EditText) v).getSelectionStart());
                }
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

    private RichEditText.onSelectionChangedListener onSelectionChangedListener = new RichEditText.onSelectionChangedListener() {
        @Override
        public void onSelectionChanged(int start, int end) {
            //todo 选中后回调，实时更新样式
            if (start == end) {
                return;
            }
            if (mOnEditEvent != null) {
                mOnEditEvent.onEditSelect(index, start, end);
            }
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
                holder.mIv.setTag(Integer.MAX_VALUE, STYLE_IMG_NORMAL);
                break;
            case STYLE_IMG_NORMAL:
                mHolderShow.add(holder);
                holder.mIvDelete.setVisibility(View.VISIBLE);
                holder.mIv.requestFocus();
                ShapeBuilder.create()
                        .Stroke(5, Color.parseColor("#FE6568"))
                        .build(holder.mIv);
                holder.mIv.setTag(Integer.MAX_VALUE, STYLE_IMG_FOCUS);
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
                holder.mIv.setTag(Integer.MAX_VALUE, STYLE_IMG_NORMAL);
            }
        }
    }

    private void clearEditFocus() {
        for (EditText editText : mEtHolder) {
            editText.setFocusable(false);
        }
    }

    private void doEnter(View view, int pos) {
        isEnter = true;
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
            RichModel newModel = new RichModel(TYPE_EDIT, newStr, "");
            for (int i = 0; i < item.getSpanList().size(); i++) {
                SpanModel span = item.getSpanList().get(i);
                if (cPos == span.start) {
                    //如果刚好在两种样式之间，将后面的样式从前一个移除，添加到后面一个样式中
                    SpanModel model = item.getSpanList().remove(i);
                    i--;
                    model.start -= cPos;
                    model.end -= cPos;
                    newModel.getSpanList().add(model);
                } else if (cPos > span.start && cPos < span.end) {
                    //如果在一个样式之间，则需要将这个样式分割
                    SpanModel model = new SpanModel(span.param);
                    model.mSpans.addAll(factory.createSpan(span.param.getCharCodes()));
                    model.start = 0;
                    model.end = span.end - cPos;
                    span.end = cPos;
                    newModel.getSpanList().add(0, model);
                } else if (cPos < span.start) {
                    //如果在样式的前面，则后面的样式全部搬到new里面，这种情况出现在光标定在没有样式的文字之间
                    span.start -= cPos;
                    span.end -= cPos;
                    newModel.getSpanList().add(span);
                    item.getSpanList().remove(i);
                    i--;
                }
            }
            mData.add(pos + 1, newModel);
        }
        item.curIndex = item.source.length();
        index++;
        notifyDataChanged();
        if (mOnScrollIndex != null) {
            mOnScrollIndex.scroll(index + 1);//header需要+1
        }
        RichBuilder.getInstance().reset();
        if (mOnEditEvent != null) {
            mOnEditEvent.onChange();
        }
    }

    private void doDel(View view, int pos) {
        if (((EditText) view).getSelectionStart() == 0
                && ((EditText) view).getSelectionStart() == ((EditText) view).getSelectionEnd()) {
            if (pos >= 1) {
                if (mData.get(pos - 1).type == TYPE_EDIT) {
                    RichModel removeModel = mData.get(pos);
                    for (SpanModel spanModel : removeModel.getSpanList()) {
                        spanModel.start += mData.get(pos - 1).source.length();
                        spanModel.end += mData.get(pos - 1).source.length();
                        mData.get(pos - 1).getSpanList().add(spanModel);
                    }
                    mData.get(pos - 1).append(mData.get(pos).source);
                    RichModelHelper.formatSpan(mData.get(pos - 1).getSpanList());
                    mData.remove(pos);
                    mEtHolder.remove(view);
                } else {
                    //KeyBoardUtils.hideSoftInputMethod(mContext);
                }
                index--;
                notifyDataChanged();
                if (mOnEditEvent != null) {
                    mOnEditEvent.onChange();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return hasHeader ? mData.size() + 1 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader) {
            if (position == 0) {
                return TYPE_HEADER;
            }
        }
        return mData.get(hasHeader ? position - 1 : position).type;
    }

    public void notifyDataChanged() {
        if (mData.size() == 1 && mData.get(0).type == TYPE_EDIT) {
            mData.get(0).hint = DEFAULT_HINT;
        }
        notifyDataSetChanged();
    }

    private class EditHolder extends RecyclerView.ViewHolder {
        private SpanStep2Filter textWatcher;
        private RichEditText mEt;
        private SpanStep1Filter filter;

        EditHolder(View itemView) {
            super(itemView);
            mEt = (RichEditText) itemView;
            mEt.setOnClickListener(onClickListener);
            textWatcher = new SpanStep2Filter(mEt, mData);
            filter = new SpanStep1Filter(mEt, mData, paramManger, factory);
            mEt.addTextChangedListener(textWatcher);
            mEt.setOnKeyListener(onKeyListener);
            mEt.setOnSelectionChangedListener(onSelectionChangedListener);
            mEt.setFilters(new InputFilter[]{filter});
        }
    }

    private class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView mIv;
        private EasyTextView mIvDelete;

        ImageHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.iv_rich_img);
            mIvDelete = (EasyTextView) itemView.findViewById(R.id.etv_rich_delete);
        }
    }

    private class HeadHolder extends RecyclerView.ViewHolder {
        HeadHolder(View mHeader) {
            super(mHeader);
        }
    }

}
