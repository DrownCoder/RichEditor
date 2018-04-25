package com.study.xuan.editor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.xuan.editor.R;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.operate.paragraph.ParagraphHelper;
import com.study.xuan.editor.operate.span.richspan.MultiSpannableString;

import java.util.ArrayList;
import java.util.List;

import static com.study.xuan.editor.adapter.RichAdapter.TYPE_HEADER;
import static com.study.xuan.editor.common.Const.TYPE_EDIT;
import static com.study.xuan.editor.common.Const.TYPE_IMG;

/**
 * Author : xuan.
 * Date : 2018/4/25.
 * Description :input the description of this file
 */

public class RichShowAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<RichModel> mData;
    private MultiSpannableString mSpanString;
    private ParagraphHelper paragraphHelper;

    public RichShowAdapter(Context mContext, List<RichModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mSpanString = new MultiSpannableString();
        paragraphHelper = new ParagraphHelper(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_EDIT:
                return new TextViewHolder(inflater.inflate(R.layout.item_text_show, null, false));
            case TYPE_IMG:
                return new ImageHolder(inflater.inflate(R.layout.item_img_show, null, false));
            case TYPE_HEADER://SingleText
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_EDIT://编辑框
                bindTextComponent(holder, position - 1, mData.get(position - 1));
                break;
            case TYPE_IMG://图片
                bindImgComponent(holder, position - 1, mData.get(position - 1));
                break;
        }
    }

    private void bindImgComponent(RecyclerView.ViewHolder holder, int i, RichModel item) {
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imgViewHolder = (ImageViewHolder) holder;
            ImageView iv = imgViewHolder.mIv;
            Glide.with(mContext).load(item.source).into(iv);
        }

    }

    private void bindTextComponent(RecyclerView.ViewHolder holder, int i, RichModel item) {
        if (holder instanceof TextViewHolder) {
            TextViewHolder textHolder = (TextViewHolder) holder;
            TextView tv = textHolder.mTv;
            if (item.isParagraphStyle) {
                SpannableStringBuilder spannableString = new SpannableStringBuilder(item.source);
                for (Object obj : item.paragraphSpan.mSpans) {
                    if (obj instanceof AbsoluteSizeSpan) {
                        AbsoluteSizeSpan sizeSpan = (AbsoluteSizeSpan) obj;
                        tv.setTextSize(sizeSpan.getSize());
                        continue;
                    }
                    spannableString.setSpan(obj, 0, item.source.length(), Spanned
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tv.setText(spannableString);
                paragraphHelper.handleTextStyle(tv, item.paragraphSpan.paragraphType);
            } else {
                mSpanString.clear();
                mSpanString.clearSpans();
                mSpanString.append(item.source);
                for (SpanModel span : item.getSpanList()) {
                    mSpanString.setMultiSpans(span.mSpans, span.start, span.end, Spanned
                            .SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tv.setText(mSpanString);
                paragraphHelper.handleTextStyle(tv, -1);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() == 0) {
            return 0;
        }
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return mData.get(position - 1).type;
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;

        public TextViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView;
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIv;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView;
        }
    }

}
