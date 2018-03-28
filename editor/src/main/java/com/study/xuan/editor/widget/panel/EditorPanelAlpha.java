package com.study.xuan.editor.widget.panel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.study.xuan.editor.R;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.RichBuilder;

/**
 * Author : xuan.
 * Date : 18-3-7.
 * Description : Alpha Editor
 */
public class EditorPanelAlpha extends LinearLayout {
    private ImageView mIvFont;
    private ImageView mIvBold, mIvItalics, mIvCenterLine, mIvUnderLine, mIvLink;
    private HorizontalScrollView mFontPanel;
    private IPanel panel;

    public EditorPanelAlpha(Context context) {
        this(context, null);
    }

    public EditorPanelAlpha(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorPanelAlpha(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View root = LayoutInflater.from(context).inflate(R.layout.editor_panel_alpha, this);
        panel = RichBuilder.getInstance().getPanelBuilder();
        initView(root);
        initEvent();
    }

    private void initEvent() {
        mIvFont.setOnClickListener(onClickListener);
        mIvBold.setOnClickListener(onClickListener);
        mIvItalics.setOnClickListener(onClickListener);
        mIvCenterLine.setOnClickListener(onClickListener);
        mIvUnderLine.setOnClickListener(onClickListener);
        mIvLink.setOnClickListener(onClickListener);
        panel.setReverse(onPanelReverse);
    }

    private void initView(View root) {
        mIvFont = root.findViewById(R.id.iv_font);
        mIvBold = root.findViewById(R.id.iv_bold);
        mIvItalics = root.findViewById(R.id.iv_italics);
        mIvCenterLine = root.findViewById(R.id.iv_center_line);
        mIvUnderLine = root.findViewById(R.id.iv_under_line);
        mFontPanel = root.findViewById(R.id.hs_font_panel);
        mIvLink = root.findViewById(R.id.iv_link);
    }

    private onPanelReverse onPanelReverse = new onPanelReverse() {
        @Override
        public void onReverse(FontParam param) {
            if (param != null) {
                mIvBold.setSelected(param.isBold);
                panel.setBold(param.isBold);
                mIvItalics.setSelected(param.isItalics);
                panel.setItalics(param.isItalics);
                mIvCenterLine.setSelected(param.isCenterLine);
                panel.setCenterLine(param.isCenterLine);
                mIvUnderLine.setSelected(param.isUnderLine);
                panel.setUnderLine(param.isUnderLine);
            } else {
                reset();
            }
        }
    };

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_font) {
                mIvFont.setSelected(!mIvFont.isSelected());
                mFontPanel.setVisibility(mIvFont.isSelected() ? VISIBLE : GONE);
            } else if (v.getId() == R.id.iv_bold) {
                mIvBold.setSelected(!mIvBold.isSelected());
                panel.setBold(mIvBold.isSelected()).change();
            } else if (v.getId() == R.id.iv_italics) {
                mIvItalics.setSelected(!mIvItalics.isSelected());
                panel.setItalics(mIvItalics.isSelected()).change();
            } else if (v.getId() == R.id.iv_center_line) {
                mIvCenterLine.setSelected(!mIvCenterLine.isSelected());
                panel.setCenterLine(mIvCenterLine.isSelected()).change();
            } else if (v.getId() == R.id.iv_under_line) {
                mIvUnderLine.setSelected(!mIvUnderLine.isSelected());
                panel.setUnderLine(mIvUnderLine.isSelected()).change();
            } else if (v.getId() == R.id.iv_link) {
                mIvLink.setSelected(!mIvLink.isSelected());
                panel.setUrl("百度", "www.baidu.com").change();
            }
        }
    };

    private void reset() {
        mFontPanel.setVisibility(View.VISIBLE);
        mIvFont.setSelected(true);
        mIvBold.setSelected(false);
        mIvItalics.setSelected(false);
        mIvCenterLine.setSelected(false);
        mIvUnderLine.setSelected(false);
        panel.reset();
    }

}
