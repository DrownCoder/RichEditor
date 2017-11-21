package com.study.xuan.editor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.xuan.editor.R;
import com.study.xuan.editor.model.panel.FontScorll;
import com.study.xuan.editor.model.panel.ModelWrapper;
import com.study.xuan.editor.model.panel.SingleComponent;
import com.study.xuan.editor.model.panel.SingleImg;
import com.study.xuan.editor.model.panel.SingleText;
import com.study.xuan.editor.util.DensityUtil;

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
                vh = new StyleHolder(inflater.inflate(R.layout.item_font_style, parent, false));
                break;
            case PANEL_FONT_SIZE:
                vh = new ScorllPanelHolder(inflater.inflate(R.layout.item_font_size, parent,
                        false));
                break;
            case PANEL_FONT_COLOR:
                vh = new ScorllPanelHolder(inflater.inflate(R.layout.item_font_size, parent, false));
                break;
            case PANEL_HEADER:
                vh = new HeaderHolder(inflater.inflate(R.layout.item_header, parent, false));
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
        ModelWrapper wrapper = mDatas.get(position);
        if (wrapper.type == PANEL_FONT_SIZE) {
            SingleText item = (SingleText) wrapper.obj;
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.mTvHeader.setText(item.desc);
            headerHolder.mTvHeader.setTextSize(item.headerSize);
        }
    }

    private void bindSizePanel(RecyclerView.ViewHolder holder, int position) {
        ModelWrapper wrapper = mDatas.get(position);
        if (wrapper.type == PANEL_FONT_SIZE) {
            FontScorll fontScorll = (FontScorll) wrapper.obj;
            ScorllPanelHolder scorllPanelHolder = (ScorllPanelHolder) holder;
            scorllPanelHolder.mTvFloorName.setText(fontScorll.floorName);
            scorllPanelHolder.mTvShow.setVisibility(View.VISIBLE);
            scorllPanelHolder.mIvShow.setVisibility(View.GONE);
            scorllPanelHolder.mTvShow.setText(((SingleText) fontScorll.items.get(0)).desc);
            ViewGroup.LayoutParams params = scorllPanelHolder.mTvShow.getLayoutParams();
            for (SingleComponent component : fontScorll.items) {
                TextView item = new TextView(mContext);
                item.setText(((SingleText) component).desc);
                int padding = DensityUtil.dp2px(mContext, 15);
                item.setPadding(padding, padding, padding, padding);
                item.setLayoutParams(params);
                scorllPanelHolder.mScrollPanel.addView(item);
            }
        }
    }

    private void bindColorPanel(RecyclerView.ViewHolder holder, int position) {
        ModelWrapper wrapper = mDatas.get(position);
        if (wrapper.type == PANEL_FONT_COLOR) {
            FontScorll fontScorll = (FontScorll) wrapper.obj;
            ScorllPanelHolder scorllPanelHolder = (ScorllPanelHolder) holder;
            scorllPanelHolder.mTvFloorName.setText(fontScorll.floorName);
            scorllPanelHolder.mTvShow.setVisibility(View.GONE);
            scorllPanelHolder.mIvShow.setVisibility(View.VISIBLE);
            scorllPanelHolder.mIvShow.setImageResource(((SingleImg) fontScorll.items.get(0))
                    .drawablePath);

            ViewGroup.LayoutParams params = scorllPanelHolder.mIvShow.getLayoutParams();
            for (SingleComponent component : fontScorll.items) {
                ImageView item = new ImageView(mContext);
                item.setImageResource(((SingleImg) component).drawablePath);
                item.setLayoutParams(params);
                int padding = DensityUtil.dp2px(mContext, 10);
                item.setPadding(padding, padding, padding, padding);
                scorllPanelHolder.mScrollPanel.addView(item);
            }
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

    private class ScorllPanelHolder extends RecyclerView.ViewHolder {
        TextView mTvFloorName;
        TextView mTvShow;
        ImageView mIvShow;
        LinearLayout mScrollPanel;

        public ScorllPanelHolder(View root) {
            super(root);
            mTvFloorName = root.findViewById(R.id.tv_floor_name);
            mTvShow = root.findViewById(R.id.tv_show);
            mIvShow = root.findViewById(R.id.iv_show);
            mScrollPanel = root.findViewById(R.id.hscorll_panel);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        TextView mTvHeader;
        public HeaderHolder(View root) {
            super(root);
            mTvHeader = root.findViewById(R.id.tv_header);
        }
    }
}
