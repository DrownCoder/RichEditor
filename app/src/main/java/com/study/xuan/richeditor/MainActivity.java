package com.study.xuan.richeditor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.model.panel.state.ParagraphChangeEvent;
import com.study.xuan.editor.operate.param.IParamManger;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.RichBuilder;
import com.study.xuan.editor.operate.parse.ParseAsyncTask;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.util.DensityUtil;
import com.study.xuan.editor.widget.RichEditor;
import com.study.xuan.editor.widget.panel.EditorPanelAlpha;
import com.study.xuan.editor.widget.panel.IPanel;
import com.study.xuan.editor.widget.panel.onPanelStateChange;

import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_FONT;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_LINK;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PANEL;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PARAGRAPH;


public class MainActivity extends Activity {
    TextView mTvSubmit;
    RichEditor mEditor;
    EditorPanelAlpha mPanel;
    IParamManger paramManager;
    IAbstractSpanFactory spanFactory;
    IPanel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditor = (RichEditor) findViewById(R.id.editor);
        mPanel = (EditorPanelAlpha) findViewById(R.id.panel);
        mTvSubmit = findViewById(R.id.tv_submit);

        paramManager = RichBuilder.getInstance().getManger();
        spanFactory = RichBuilder.getInstance().getFactory();
        panel = RichBuilder.getInstance().getPanelBuilder();
        panel.setStateChange(new onPanelStateChange() {
            @Override
            public void onStateChanged() {
                switch (panel.getType()) {
                    case TYPE_FONT:
                        onFontEvent(panel.getFontParam());
                        break;
                    case TYPE_LINK:
                        onLinkEvent(panel.getFontParam());
                        break;
                    case TYPE_PARAGRAPH:
                        onParagraphEvent(panel.getParagraph().type);
                        break;
                    case TYPE_PANEL:
                        onPanelEvent(panel.isShow());
                        break;
                }
            }
        });
       /* mPanel.setStateChange(new EditorPanel.onPanelStateChange() {
            @Override
            public void onStateChanged(BasePanelEvent state) {
                if (state instanceof FontChangeEvent) {
                    onFontEvent(state.isSelected);
                } else if (state instanceof ParagraphChangeEvent) {
                    onParagraphEvent((ParagraphChangeEvent)state);
                } else if (state instanceof LinkChangeEvent) {
                    onLinkEvent((LinkChangeEvent) state);
                }
            }
        });*/
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseAsyncTask saveTask = new ParseAsyncTask(mEditor.getData());
                saveTask.execute(Const.MARKDOWN_PARSE_TYPE);
            }
        });
    }

    private void onPanelEvent(boolean show) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mEditor.getLayoutParams();
        if (show) {
            params.bottomMargin = DensityUtil.dp2px(this, 100);
        } else {
            params.bottomMargin = DensityUtil.dp2px(this, 50);
        }
    }

    /**
     * 链接事件
     */
    /*private void onLinkEvent(LinkChangeEvent state) {
        SpanModel linkModel = new SpanModel(state.linkParam);
        linkModel.code = state.linkParam.getCharCodes();
        linkModel.mSpans = spanFactory.createSpan(linkModel.code);

        mEditor.getCurIndexModel().addSpanModel(state.title, linkModel);
        mEditor.notifyEvent();

        SpanModel spanModel = new SpanModel(paramManager.createNewParam());
        spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
        spanModel.mSpans = spanFactory.createSpan(spanModel.code);
        mEditor.getCurIndexModel().setNewSpan(spanModel);
    }*/
    private void onLinkEvent(FontParam param) {
        SpanModel linkModel = new SpanModel(param);
        linkModel.code = param.getCharCodes();
        linkModel.mSpans = spanFactory.createSpan(linkModel.code);

        mEditor.insertSpan(param.name, linkModel);
        mEditor.notifyEvent();
        RichBuilder.getInstance().reset();

        /*SpanModel spanModel = new SpanModel(paramManager.createNewParam());
        spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
        spanModel.mSpans = spanFactory.createSpan(spanModel.code);
        mEditor.getCurIndexModel().setNewSpan(spanModel);*/
    }

    /**
     * 段落样式改变事件
     */
    private void onParagraphEvent(int pType) {
        initParagraphSpan(pType);
        mEditor.notifyEvent();
    }

    private void onParagraphEvent(ParagraphChangeEvent state) {
        if (!state.isSelected) {
            mEditor.getCurIndexModel().setNoParagraphSpan();
            mEditor.notifyEvent();
            return;
        }
        initParagraphSpan(state.pType);
        mEditor.notifyEvent();
    }

    private void initParagraphSpan(int pType) {
        if (pType != Const.PARAGRAPH_NONE) {
            SpanModel model = new SpanModel(pType);
            model.code = paramManager.getParamCode(pType);
            model.mSpans = spanFactory.createSpan(model.code);
            mEditor.getCurIndexModel().setParagraphSpan(model);
        } else {
            mEditor.getCurIndexModel().setNoParagraphSpan();
        }
    }

    /**
     * 字体样式改变事件
     */
    /*private void onFontEvent(boolean isShow) {
        if (!isShow) {
            if (paramManager.needNewSpan(mPanel.getFontParams())) {//需要新生产span样式
                SpanModel spanModel = new SpanModel(paramManager.createNewParam());
                spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
                spanModel.mSpans = spanFactory.createSpan(spanModel.code);
                mEditor.getCurIndexModel().setNewSpan(spanModel);
            } else {
                mEditor.getCurIndexModel().setNoNewSpan();
            }
        }else{
            KeyboardUtil.closeKeyboard(this);
        }
    }*/
    private void onFontEvent(FontParam param) {
        if (checkEditorSelectStatus()) {
            //todo 分割样式，后添加样式
            return;
        }
        if (paramManager.needNewSpan(param)) {//需要新生产span样式
            SpanModel spanModel = new SpanModel(paramManager.createNewParam());
            spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
            spanModel.mSpans = spanFactory.createSpan(spanModel.code);
            mEditor.getCurIndexModel().setNewSpan(spanModel);
        } else {
            mEditor.getCurIndexModel().setNoNewSpan();
        }
        mEditor.saveInfo();
        mEditor.notifyEvent();
    }

    /**
     * 编辑器处于选中状态
     */
    private boolean checkEditorSelectStatus() {
        EditText etCur = mEditor.getCurEditText();
        if (etCur.getSelectionStart() != etCur.getSelectionEnd()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichBuilder.getInstance().destroy();
    }
}
