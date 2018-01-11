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
import com.study.xuan.editor.operate.FontParamBuilder;
import com.study.xuan.editor.util.DensityUtil;
import com.study.xuan.editor.util.EditorUtil;
import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.GradientDrawable.OVAL;
import static com.study.xuan.editor.common.Const.FONT_BACKGROUND;
import static com.study.xuan.editor.common.Const.FONT_BOLD;
import static com.study.xuan.editor.common.Const.FONT_ITALICS;
import static com.study.xuan.editor.common.Const.FONT_MIDLINE;
import static com.study.xuan.editor.common.Const.FONT_UNDERLINE;
import static com.study.xuan.editor.common.Const.PANEL_FONT_COLOR;
import static com.study.xuan.editor.common.Const.PANEL_FONT_SIZE;
import static com.study.xuan.editor.common.Const.PANEL_FONT_STYLE;
import static com.study.xuan.editor.common.Const.PANEL_HEADER;

/**
 * Author : xuan.
 * Date : 2017/11/17.
 * Description :操作盘
 */

public class PanelAdapter extends RecyclerView.Adapter {
    public static final String TAG = "PanelAdapter";
    private List<ModelWrapper> mDatas;
    private Context mContext;
    private FontParamBuilder builder;

    private int FONT_SIZE_PADDING;
    private int FONT_COLOR_PADDING;
    private LinearLayout.LayoutParams colorParams;

    private List<TextView> mSizeViews;


    public PanelAdapter(Context context, List<ModelWrapper> mDatas, FontParamBuilder paramBuilder) {
        this.mDatas = mDatas;
        this.mContext = context;
        builder = paramBuilder;
        mSizeViews = new ArrayList<>();
        FONT_SIZE_PADDING = DensityUtil.dp2px(mContext, 15);
        FONT_COLOR_PADDING = DensityUtil.dp2px(mContext, 1.5f);
        int FONT_COLOR_MARGINT = DensityUtil.dp2px(mContext, 5);
        colorParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(mContext, 35), DensityUtil
                .dp2px(mContext, 35));
        colorParams.setMargins(FONT_COLOR_MARGINT, FONT_COLOR_MARGINT, FONT_COLOR_MARGINT,
                FONT_COLOR_MARGINT);
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
                vh = new ScrollPanelHolder(inflater.inflate(R.layout.item_font_size, parent,
                        false));
                break;
            case PANEL_FONT_COLOR:
                vh = new ScrollPanelHolder(inflater.inflate(R.layout.item_font_size, parent,
                        false));
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
                break;
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
            ScrollPanelHolder scrollPanelHolder = (ScrollPanelHolder) holder;
            scrollPanelHolder.mTvFloorName.setText(fontScorll.floorName);
            scrollPanelHolder.mTvShow.setVisibility(View.VISIBLE);
            scrollPanelHolder.mIvShow.setVisibility(View.GONE);
            scrollPanelHolder.mTvShow.setText(((SingleText) fontScorll.items.get(0)).desc);
            ViewGroup.LayoutParams params = scrollPanelHolder.mTvShow.getLayoutParams();
            scrollPanelHolder.mScrollPanel.removeAllViews();
            for (SingleComponent component : fontScorll.items) {
                TextView item = new TextView(mContext);
                item.setText(((SingleText) component).desc);
                item.setPadding(FONT_SIZE_PADDING, FONT_SIZE_PADDING, FONT_SIZE_PADDING,
                        FONT_SIZE_PADDING);
                item.setLayoutParams(params);
                item.setTag(((SingleText) component).desc);
                item.setOnClickListener(onClickListener);
                if (((SingleText) component).desc.equals(String.valueOf(builder.getFontSize()))) {
                    item.getPaint().setFakeBoldText(true);
                }
                scrollPanelHolder.mScrollPanel.addView(item);
            }
            if (builder.getFontSize() != 0) {
                scrollPanelHolder.mTvShow.setText(String.valueOf((int)DensityUtil.px2sp(mContext,builder.getFontSize())));
            }
        }
    }

    private void bindColorPanel(RecyclerView.ViewHolder holder, int position) {
        ModelWrapper wrapper = mDatas.get(position);
        if (wrapper.type == PANEL_FONT_COLOR) {
            FontScorll fontScorll = (FontScorll) wrapper.obj;
            ScrollPanelHolder scrollPanelHolder = (ScrollPanelHolder) holder;
            scrollPanelHolder.mTvFloorName.setText(fontScorll.floorName);
            scrollPanelHolder.mTvShow.setVisibility(View.GONE);
            scrollPanelHolder.mIvShow.setVisibility(View.VISIBLE);

            scrollPanelHolder.mScrollPanel.removeAllViews();
            for (SingleComponent component : fontScorll.items) {
                int color = EditorUtil.getResourcesColor(mContext, ((SingleImg)component).styleType);
                ImageView item = new ImageView(mContext);
                item.setImageResource(((SingleImg) component).drawablePath);
                item.setLayoutParams(colorParams);
                item.setPadding(0, 0, FONT_COLOR_PADDING / 2, FONT_COLOR_PADDING);
                item.setScaleType(ImageView.ScaleType.FIT_XY);
                item.setOnClickListener(onClickListener);
                item.setTag(color);
                if (color == builder.getFontColor()) {
                    ShapeBuilder.create()
                            .Type(OVAL)
                            .Stroke(4, color)
                            .build(item);
                }
                scrollPanelHolder.mScrollPanel.addView(item);
            }
            if (builder.getFontColor() != 0) {
                ShapeBuilder.create()
                        .Type(OVAL)
                        .Soild(builder.getFontColor())
                        .build(scrollPanelHolder.mIvShow);
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
            styleHolder.mIvStyle.setTag(img.styleType);
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

        StyleHolder(View root) {
            super(root);
            mIvStyle = root.findViewById(R.id.iv_font_style);
            mIvStyle.setOnClickListener(onClickListener);
        }
    }

    private class ScrollPanelHolder extends RecyclerView.ViewHolder {
        TextView mTvFloorName;
        TextView mTvShow;
        ImageView mIvShow;
        LinearLayout mScrollPanel;

        ScrollPanelHolder(View root) {
            super(root);
            mTvFloorName = root.findViewById(R.id.tv_floor_name);
            mTvShow = root.findViewById(R.id.tv_show);
            mIvShow = root.findViewById(R.id.iv_show);
            mScrollPanel = root.findViewById(R.id.hscorll_panel);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        TextView mTvHeader;

        HeaderHolder(View root) {
            super(root);
            mTvHeader = root.findViewById(R.id.tv_header);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setSelected(!v.isSelected());
            if (v.getId() == R.id.iv_font_style) {//字体样式
                setSytleParam(Integer.parseInt(v.getTag().toString()), v.isSelected());
            } else {
                if (v instanceof TextView) {//字号
                    if (v.isSelected()) {
                        clearAllSizeSelected();
                        v.setSelected(true);
                        ((TextView) v).getPaint().setFakeBoldText(true);
                        mSizeViews.add((TextView) v);
                    }else{
                        mSizeViews.remove(v);
                        ((TextView) v).getPaint().setFakeBoldText(false);
                    }
                    builder.fontSize(DensityUtil.sp2px(mContext, Integer.valueOf(v.getTag()
                            .toString())));
                } else {//字色
                    builder.fontColor(Integer.valueOf(v.getTag().toString()));
                }
            }
            notifyDataSetChanged();
        }
    };

    /**
     * 清楚所有选中的size
     */
    private void clearAllSizeSelected() {
        for (TextView item : mSizeViews) {
            item.setSelected(false);
            item.getPaint().setFakeBoldText(false);
        }
    }


    /**
     * 设置字体样式
     */
    private void setSytleParam(int type, boolean selected) {
        switch (type) {
            case FONT_BOLD:
                builder.isBold(selected);
                break;
            case FONT_ITALICS:
                builder.isItalics(selected);
                break;
            case FONT_UNDERLINE:
                builder.isUnderLine(selected);
                break;
            case FONT_MIDLINE:
                builder.isCenterLine(selected);
                break;
            case FONT_BACKGROUND:
                builder.isFontBac(selected);
                break;
        }
    }
}
