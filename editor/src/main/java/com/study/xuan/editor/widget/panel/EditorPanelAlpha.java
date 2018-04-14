package com.study.xuan.editor.widget.panel;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.xuan.editor.R;
import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.panel.LinkModel;
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
    private ImageView mIvRefer;
    private TextView mTvH1, mTvH2, mTvH3, mTvH4;
    private HorizontalScrollView mFontPanel;
    private IPanel panel;
    private Context mContext;

    public EditorPanelAlpha(Context context) {
        this(context, null);
    }

    public EditorPanelAlpha(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorPanelAlpha(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
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
        mIvRefer.setOnClickListener(onClickListener);
        mIvLink.setOnClickListener(onClickListener);
        mTvH1.setOnClickListener(onClickListener);
        mTvH2.setOnClickListener(onClickListener);
        mTvH3.setOnClickListener(onClickListener);
        mTvH4.setOnClickListener(onClickListener);
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
        mIvRefer = root.findViewById(R.id.iv_refer);
        mTvH1 = root.findViewById(R.id.tv_h1);
        mTvH2 = root.findViewById(R.id.tv_h2);
        mTvH3 = root.findViewById(R.id.tv_h3);
        mTvH4 = root.findViewById(R.id.tv_h4);
    }

    private onPanelReverse onPanelReverse = new onPanelReverse() {
        @Override
        public void onReverse(FontParam param, int paragraphType) {
            setParagraphStyle(paragraphType);
            setFontStyle(param);
        }
    };

    private void setFontStyle(FontParam param) {
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
            resetFont();
        }
    }

    private void setParagraphStyle(int paragraphType) {
        resetParagraph();
        switch (paragraphType) {
            case Const.PARAGRAPH_REFER:
                mIvRefer.setSelected(true);
                break;
            case Const.PARAGRAPH_T1:
                mTvH1.setSelected(true);
                break;
            case Const.PARAGRAPH_T2:
                mTvH2.setSelected(true);
                break;
            case Const.PARAGRAPH_T3:
                mTvH3.setSelected(true);
                break;
            case Const.PARAGRAPH_T4:
                mTvH4.setSelected(true);
                break;
        }
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_font) {
                mIvFont.setSelected(!mIvFont.isSelected());
                mFontPanel.setVisibility(mIvFont.isSelected() ? VISIBLE : GONE);
                panel.showPanel(mIvFont.isSelected()).change();
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
                LinkDialogFragment linkDialog = LinkDialogFragment.newInstance();
                linkDialog.show(((Activity) mContext).getFragmentManager(), "Link");
                linkDialog.setOnSureClickListener(new LinkDialogFragment.onSureClickListener() {
                    @Override
                    public void onSure(LinkModel linkModel) {
                        panel.setUrl(linkModel.name, linkModel.link).change();
                    }
                });
            } else if (v.getId() == R.id.iv_refer) {
                resetParagraph(v);
                mIvRefer.setSelected(!mIvRefer.isSelected());
                panel.setRefer(mIvRefer.isSelected()).change();
            } else if (v.getId() == R.id.tv_h1) {
                resetParagraph(v);
                mTvH1.setSelected(!mTvH1.isSelected());
                panel.setH1(v.isSelected()).change();
            } else if (v.getId() == R.id.tv_h2) {
                resetParagraph(v);
                mTvH2.setSelected(!mTvH2.isSelected());
                panel.setH2(v.isSelected()).change();
            } else if (v.getId() == R.id.tv_h3) {
                resetParagraph(v);
                mTvH3.setSelected(!mTvH3.isSelected());
                panel.setH3(v.isSelected()).change();
            } else if (v.getId() == R.id.tv_h4) {
                resetParagraph(v);
                mTvH4.setSelected(!mTvH4.isSelected());
                panel.setH4(v.isSelected()).change();
            }
        }
    };

    private void resetFont() {
        mIvBold.setSelected(false);
        mIvItalics.setSelected(false);
        mIvCenterLine.setSelected(false);
        mIvUnderLine.setSelected(false);
        panel.resetFont();
    }

    private void resetParagraph(View view) {
        boolean isSelect = false;
        if (view != null) {
            isSelect = view.isSelected();
        }
        mTvH1.setSelected(false);
        mTvH2.setSelected(false);
        mTvH3.setSelected(false);
        mTvH4.setSelected(false);
        mIvRefer.setSelected(false);
        if (view != null) {
            view.setSelected(isSelect);
        }
    }

    private void resetParagraph() {
        resetParagraph(null);
    }

}
