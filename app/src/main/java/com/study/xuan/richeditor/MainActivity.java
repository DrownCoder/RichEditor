package com.study.xuan.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.model.panel.event.BasePanelEvent;
import com.study.xuan.editor.model.panel.event.FontChangeEvent;
import com.study.xuan.editor.model.panel.event.LinkChangeEvent;
import com.study.xuan.editor.model.panel.event.ParagraphChangeEvent;
import com.study.xuan.editor.operate.ParamManager;
import com.study.xuan.editor.operate.span.factory.AbstractSpanFactory;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.util.KeyboardUtil;
import com.study.xuan.editor.widget.EditorPanel;
import com.study.xuan.editor.widget.RichEditor;


public class MainActivity extends AppCompatActivity {
    RichEditor mEditor;
    EditorPanel mPanel;
    ParamManager paramManager;
    IAbstractSpanFactory spanFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditor = (RichEditor) findViewById(R.id.editor);
        mPanel = (EditorPanel) findViewById(R.id.panel);
        paramManager = new ParamManager();
        spanFactory = new AbstractSpanFactory();

        mPanel.setStateChange(new EditorPanel.onPanelStateChange() {
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
        });
    }

    /**
     * 链接事件
     */
    private void onLinkEvent(LinkChangeEvent state) {
        SpanModel linkModel = new SpanModel(state.linkParam);
        linkModel.code = state.linkParam.getCharCodes();
        linkModel.mSpans = spanFactory.createSpan(linkModel.code);

        mEditor.getCurIndexModel().addLink(state.title, linkModel);
        mEditor.notifyEvent();

        SpanModel spanModel = new SpanModel(paramManager.createNewParam());
        spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
        spanModel.mSpans = spanFactory.createSpan(spanModel.code);
        mEditor.getCurIndexModel().setNewSpan(spanModel);
    }

    /**
     * 段落样式改变事件
     */
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
        SpanModel model = new SpanModel(pType);
        model.code = paramManager.getParamCode(pType);
        model.mSpans = spanFactory.createSpan(model.code);
        mEditor.getCurIndexModel().setParagraphSpan(model);
    }

    /**
     * 字体样式改变事件
     */
    private void onFontEvent(boolean isShow) {
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
    }

}
