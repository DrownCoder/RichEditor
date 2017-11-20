package com.study.xuan.editor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.xuan.editor.R;
import com.study.xuan.editor.model.panel.ModelWrapper;
import com.study.xuan.editor.model.panel.SingleImg;

import java.util.List;

import static com.study.xuan.editor.typeholder.ViewType.PANEL_FONT_COLOR;
import static com.study.xuan.editor.typeholder.ViewType.PANEL_FONT_SIZE;
import static com.study.xuan.editor.typeholder.ViewType.PANEL_FONT_STYLE;
import static com.study.xuan.editor.typeholder.ViewType.PANEL_HEADER;

/**
 * Author : xuan.
 * Date : 2017/11/17.
 * Description :input the description of this file.
 */

public class PanelAdapter extends RecyclerView.Adapter {
    private List<ModelWrapper> mDatas;
    private Context mContext;

    public PanelAdapter(Context context, List<ModelWrapper> mDatas) {
        this.mDatas = mDatas;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder vh = null;
        switch (viewType) {
            case PANEL_FONT_STYLE:
                vh = new StyleHolder(inflater.inflate(R.layout.item_font_style, parent));
                break;
            case PANEL_FONT_SIZE:
                vh = new SizeHolder(inflater.inflate(R.layout.item_font_size, parent));
                break;
            case PANEL_FONT_COLOR:
                vh = new ColorHolder(inflater.inflate(R.layout.item_font_size, parent));
                break;
            case PANEL_HEADER:
                vh = new HeaderHolder(inflater.inflate(R.layout.item_header, parent));
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case PANEL_FONT_STYLE:
                bindStylePanel(holder,position);
                break;
            case PANEL_FONT_SIZE:
                bindSizePanel(holder, position);
                break;
            case PANEL_FONT_COLOR:
                bindColorPanel(holder, position);
                break;
            case PANEL_HEADER:
                bindHeaderPanel(holder, position);
        }
    }

    private void bindHeaderPanel(RecyclerView.ViewHolder holder, int position) {

    }

    private void bindSizePanel(RecyclerView.ViewHolder holder, int position) {

    }

    private void bindColorPanel(RecyclerView.ViewHolder holder, int position) {
        ModelWrapper wrapper = mDatas.get(position);
        if (wrapper.type == PANEL_FONT_COLOR) {

        }
    }

    /**
     * 字样式
     */
    private void bindStylePanel(RecyclerView.ViewHolder holder, int position) {
        ModelWrapper wrapper = mDatas.get(position);
        if (wrapper.type == PANEL_FONT_STYLE) {
            SingleImg img = (SingleImg) wrapper.obj;
            StyleHolder styleHolder = (StyleHolder) holder;
            styleHolder.mIvStyle.setImageResource(img.drawablePath);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).type;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class StyleHolder extends RecyclerView.ViewHolder {
        ImageView mIvStyle;
        public StyleHolder(View root) {
            super(root);
            mIvStyle = root.findViewById(R.id.iv_font_style);
        }
    }

    private class SizeHolder extends RecyclerView.ViewHolder {
        public SizeHolder(View root) {
            super(root);
        }
    }

    private class ColorHolder extends RecyclerView.ViewHolder {
        public ColorHolder(View root) {
            super(root);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View root) {
            super(root);
        }
    }
}
