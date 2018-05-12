package com.study.xuan.editor.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.study.xuan.editor.callback.onEditorCallback;
import com.study.xuan.editor.callback.onEditorEvent;
import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SelectionInfo;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.model.panel.state.ParagraphChangeEvent;
import com.study.xuan.editor.operate.RichBuilder;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.helper.RichModelHelper;
import com.study.xuan.editor.operate.param.IParamManger;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.operate.task.ParseAsyncTask;
import com.study.xuan.editor.util.DensityUtil;
import com.study.xuan.editor.util.RichLog;
import com.study.xuan.editor.widget.panel.EditorPanelAlpha;
import com.study.xuan.editor.widget.panel.IPanel;
import com.study.xuan.editor.widget.panel.onPanelStateChange;

import java.util.ArrayList;
import java.util.List;

import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_FONT;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_LINK;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PANEL;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PARAGRAPH;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PHOTOPICKER;

/**
 * Author : xuan.
 * Date : 2018/5/11.
 * Description :richEditor+panel
 */

public class Editor extends FrameLayout {
    private Context context;
    private EditorPanelAlpha panel;
    private RichEditor richEditor;

    private IPanel panelHelper;
    private IParamManger paramManager;
    private IAbstractSpanFactory spanFactory;
    private ParseAsyncTask mTask;

    private onEditorCallback callback;

    public Editor(@NonNull Context context) {
        this(context, null);
    }

    public Editor(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Editor(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        initEvent();
    }

    private void init() {
        panel = new EditorPanelAlpha(context);
        richEditor = new RichEditor(context);
        LayoutParams panelParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        addView(panel, panelParams);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, DensityUtil.dp2px(context, 50), 0, DensityUtil.dp2px(context, 50));
        richEditor.setLayoutParams(params);
        addView(richEditor);

        paramManager = RichBuilder.getInstance().getManger();
        spanFactory = RichBuilder.getInstance().getFactory();
        panelHelper = RichBuilder.getInstance().getPanelBuilder();
    }

    private void initEvent() {
        richEditor.setOnEditorEvent(onEditorEvent);
        panelHelper.setStateChange(new onPanelStateChange() {
            @Override
            public void onStateChanged() {
                switch (panelHelper.getType()) {
                    case TYPE_FONT:
                        onFontEvent(panelHelper.getFontParam());
                        break;
                    case TYPE_LINK:
                        onLinkEvent(panelHelper.getFontParam());
                        break;
                    case TYPE_PARAGRAPH:
                        onParagraphEvent(panelHelper.getParagraphType());
                        break;
                    case TYPE_PANEL:
                        onPanelEvent(panelHelper.isShow());
                        break;
                    case TYPE_PHOTOPICKER:
                        onPhotoEvent();
                        break;
                }
            }
        });
    }


    private onEditorEvent onEditorEvent = new onEditorEvent() {
        @Override
        public void onLineCountChange(List<RichModel> data) {
            if (callback != null) {
                callback.onLineNumChange(data);
            }
        }
    };

    private void onPhotoEvent() {
        if (callback != null) {
            callback.onPhotoEvent();
        }
    }

    private void onPanelEvent(boolean show) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) richEditor.getLayoutParams();
        if (show) {
            params.bottomMargin = DensityUtil.dp2px(context, 100);
        } else {
            params.bottomMargin = DensityUtil.dp2px(context, 50);
        }
    }

    private void onLinkEvent(FontParam param) {
        SpanModel linkModel = new SpanModel(param);
        linkModel.code = param.getCharCodes();
        linkModel.mSpans = spanFactory.createSpan(linkModel.code);

        richEditor.insertSpan(param.name, linkModel);
        richEditor.notifyEvent();
        RichBuilder.getInstance().reset();
    }

    /**
     * 段落样式改变事件
     */
    private void onParagraphEvent(int pType) {
        initParagraphSpan(pType);
        richEditor.notifyEvent();
    }

    private void onParagraphEvent(ParagraphChangeEvent state) {
        if (!state.isSelected) {
            richEditor.getCurIndexModel().setNoParagraphSpan();
            richEditor.notifyEvent();
            return;
        }
        initParagraphSpan(state.pType);
        richEditor.notifyEvent();
    }

    private void initParagraphSpan(int pType) {
        if (pType != Const.PARAGRAPH_NONE) {
            SpanModel model = new SpanModel(pType);
            model.code = paramManager.getParamCode(pType);
            model.mSpans = spanFactory.createSpan(model.code);
            richEditor.getCurIndexModel().setParagraphSpan(model);
        } else {
            richEditor.getCurIndexModel().setNoParagraphSpan();
        }
    }

    /**
     * 字体样式改变事件
     */
    private void onFontEvent(FontParam param) {
        if (checkEditorSelectStatus()) {
            //当前是选中状态
            final SelectionInfo info = richEditor.getSelectionInfo();
            if (info == null) {
                return;
            }
            //清楚选中状态样式
            int index = RichModelHelper.cleanBetweenArea(richEditor.getCurIndexModel().getSpanList(), info.startIndex, info.endIndex);
            if (param.isFontParamValid()) {
                //插入新样式
                SpanModel spanModel = new SpanModel(paramManager.cloneParam(param));
                spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
                spanModel.mSpans = spanFactory.createSpan(spanModel.code);
                spanModel.start = info.startIndex;
                spanModel.end = info.endIndex;
                richEditor.getCurIndexModel().setNewSpan(spanModel);
                richEditor.getCurIndexModel().getSpanList().add(index, spanModel);
            }
            richEditor.notifyEvent();
            richEditor.getCurEditText().postDelayed(new Runnable() {
                @Override
                public void run() {
                    richEditor.getCurEditText().setSelection(info.startIndex, info.endIndex);
                }
            }, 100);
            return;
        }
        if (paramManager.needNewSpan(param)) {//需要新生产span样式
            SpanModel spanModel = new SpanModel(paramManager.createNewParam());
            spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
            spanModel.mSpans = spanFactory.createSpan(spanModel.code);
            richEditor.getCurIndexModel().setNewSpan(spanModel);
        } else {
            richEditor.getCurIndexModel().setNoNewSpan();
        }
        richEditor.saveInfo();
        richEditor.notifyEvent();
    }

    /**
     * 编辑器处于选中状态
     */
    private boolean checkEditorSelectStatus() {
        EditText etCur = richEditor.getCurEditText();
        if (etCur.getSelectionStart() != etCur.getSelectionEnd()) {
            return true;
        }
        return false;
    }

    public void startMarkDownTask() {
        mTask = new ParseAsyncTask(callback);
        mTask.setParseType(Const.MARKDOWN_PARSE_TYPE);
        mTask.execute(richEditor.getData());
    }

    public void setCallback(onEditorCallback callback) {
        this.callback = callback;
    }

    public void insertMore(View view) {
        panel.insertMore(view);
    }

    public void addPhoto(ArrayList<String> photos) {
        if (photos == null || photos.isEmpty()) {
            RichLog.error("this photo list is null or empty !!!");
            return;
        }
        richEditor.addPhoto(photos);
    }

    public void destroy() {
        if (mTask != null) {
            mTask.cancel(true);
        }
    }
}
