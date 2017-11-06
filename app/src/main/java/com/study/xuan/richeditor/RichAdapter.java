package com.study.xuan.richeditor;

import android.content.Context;
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

import java.util.HashSet;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/3.
 * Description :input the description of this file.
 */

public class RichAdapter extends RecyclerView.Adapter {
    public static final int TYPE_EDIT = 0;
    public static final int TYPE_IMG = 1;
    private HashSet<EditText> mEtHolder;
    private HashSet<ImageHolder> mHolderShow;
    private List<RichModel> mData;
    private Context mContext;
    public int index = 0;

    public interface onScrollIndex {
        void scroll(int pos);
    }

    private onScrollIndex mOnScollIndex;

    public void setmOnScollIndex(onScrollIndex mOnScollIndex) {
        this.mOnScollIndex = mOnScollIndex;
    }

    public RichAdapter(List<RichModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mEtHolder = new HashSet<>();
        mHolderShow = new HashSet<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_EDIT:
                return new EditHolder(inflater.inflate(R.layout.item_edit, null, false));
            case TYPE_IMG:
                return new ImageHolder(inflater.inflate(R.layout.item_img, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_EDIT://编辑框
                bindEditComponent(holder, position, mData.get(position));
                break;
            case TYPE_IMG://图片
                bindImgComponent(holder, position, mData.get(position));
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
                mHolderShow.add(imageHolder);
                imageHolder.mIvDelete.setVisibility(View.VISIBLE);
                imageHolder.mIv.setTag("TRUE");
                clearEditFocus();
                imageHolder.mIv.requestFocus();
            } else {
                mHolderShow.remove(imageHolder);
                imageHolder.mIvDelete.setVisibility(View.GONE);
                imageHolder.mIv.clearFocus();
                imageHolder.mIv.setTag("FALSE");
            }
            imageHolder.mIv.setBackgroundResource(0);
            imageHolder.mIvDelete.setTag(pos);
            imageHolder.mIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearEditFocus();
                    if (v.getTag().toString().equals("TRUE")) {
                        mHolderShow.remove(imageHolder);
                        imageHolder.mIvDelete.setVisibility(View.GONE);
                        imageHolder.mIv.clearFocus();
                        v.setTag("FALSE");
                    } else {
                        mHolderShow.add(imageHolder);
                        imageHolder.mIvDelete.setVisibility(View.VISIBLE);
                        imageHolder.mIv.requestFocus();
                        v.setTag("TRUE");
                    }

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
                    mData.remove((Integer.parseInt(v.getTag().toString())));
                    index--;
                    notifyDataChanged();
                    break;
                case R.id.et_rich_edit:
                    clearImgFocus();
                    v.setFocusableInTouchMode(true);
                    v.setFocusable(true);
                    v.requestFocus();
                    index = (int) v.getTag();
                    break;
            }
        }
    };

    private void clearImgFocus() {
        for (ImageHolder holder : mHolderShow) {
            holder.mIvDelete.setVisibility(View.GONE);
            holder.mIv.setTag("FALSE");
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
            mOnScollIndex.scroll(index);
        }
    }

    private void doDel(View view, int pos) {
        if (((EditText) view).getSelectionStart() == 0) {
            if (pos >= 1) {
                if (mData.get(pos - 1).type == TYPE_EDIT) {
                    mData.get(pos - 1).append(mData.get(pos).source);
                    mData.remove(pos);
                }
                index--;
                notifyDataChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
    }

    public void notifyDataChanged() {
        Log.i("index", index + "");
        notifyDataSetChanged();
    }

    class EditHolder extends RecyclerView.ViewHolder {
        private CustomEditTextListener textWatcher;
        private EditText mEt;

        public EditHolder(View itemView) {
            super(itemView);
            mEt = (EditText) itemView;
            //mEtHolder.add(mEt);
            mEt.setOnClickListener(onClickListener);
            textWatcher = new CustomEditTextListener();
            mEt.addTextChangedListener(textWatcher);
            mEt.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (keyEvent != null && keyEvent.getAction() == keyEvent.ACTION_DOWN) {
                        switch (i) {
                            case KeyEvent.KEYCODE_DEL:
                                //删除
                                doDel(view, (Integer) mEt.getTag());
                                break;
                            case KeyEvent.KEYCODE_ENTER:
                                //回车
                                doEnter(view, (Integer) mEt.getTag());
                                return true;
                        }
                    }
                    return false;
                }
            });

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

    class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView mIv;
        private ImageView mIvDelete;

        public ImageHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv_rich_img);
            mIvDelete = itemView.findViewById(R.id.iv_rich_delete);
        }
    }

    private class CustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
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

}
