package com.study.xuan.richeditor;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/3.
 * Description :input the description of this file.
 */

public class RichAdapter extends RecyclerView.Adapter {
    public static final int TYPE_EDIT = 0;
    public static final int TYPE_IMG = 1;
    private static final int TYPE_HEADER = -1;
    private final int STYLE_IMG_FOCUS = 1;
    private final int STYLE_IMG_NORMAL = 0;
    private HashSet<EditText> mEtHolder;
    private List<ImageHolder> mHolderShow;
    private List<RichModel> mData;
    private Context mContext;
    public int index = 0;

    private View mHeader;

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
            case TYPE_HEADER://header
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
            mEdit.setText(item.source);
            mEdit.setSelection(item.source.length());
            mEdit.setHint(item.defaultSource);
            mEdit.setTag(pos);
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_rich_delete:
                    int pos = (int) v.getTag();
                    if (mOnPhotoDelete != null) {
                        mOnPhotoDelete.onDelete(mData.get(pos).source);
                    }
                    mData.remove(pos);
                    index--;
                    notifyDataChanged();
                    break;
                case R.id.et_rich_edit:
                    v.setFocusableInTouchMode(true);
                    v.setFocusable(true);
                    v.requestFocus();
                    index = (int) v.getTag();
                    clearImgFocus(index);

                    break;
            }
        }
    };

    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
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
                        .Stroke(2, Color.parseColor("#000000"))
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
        int cPos = ((EditText) view).getSelectionStart();
        RichModel item = mData.get(pos);
        if (cPos == ((EditText) view).getText().length()) {//光标在末尾
            mData.add(pos + 1, new RichModel(TYPE_EDIT, "", false, ""));
        } else {
            String newStr = item.source.substring(cPos, item.source.length());
            String oldStr = item.source.substring(0, cPos);
            item.setSource(oldStr);
            mData.add(pos + 1, new RichModel(TYPE_EDIT, newStr, false, ""));
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
        Log.i("index", index + "");
        notifyDataSetChanged();
    }

    private class EditHolder extends RecyclerView.ViewHolder {
        private CustomEditTextListener textWatcher;
        private EditText mEt;

        EditHolder(View itemView) {
            super(itemView);
            mEt = (EditText) itemView;
            //mEtHolder.add(mEt);
            mEt.setOnClickListener(onClickListener);
            textWatcher = new CustomEditTextListener();
            mEt.addTextChangedListener(textWatcher);
            mEt.setOnKeyListener(onKeyListener);

        }

        public void setData(int pos) {
            if (index == pos) {
                mEt.requestFocus();
            } else {
                mEt.clearFocus();
            }
            RichModel myData = mData.get(pos);
            mEt.setText(myData.source);
            mEt.setTag(pos);
            mEt.setSelection(myData.source.length());
            mEt.setHint(myData.defaultSource);
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
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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
}
