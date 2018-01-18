package com.study.xuan.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.model.panel.state.BasePanelEvent;
import com.study.xuan.editor.model.panel.state.FontChangeEvent;
import com.study.xuan.editor.model.panel.state.ParagraphChangeEvent;
import com.study.xuan.editor.operate.ParamManager;
import com.study.xuan.editor.util.KeyboardUtil;
import com.study.xuan.editor.widget.EditorPanel;
import com.study.xuan.editor.widget.RichEditor;
import com.study.xuan.editor.operate.span.factory.AbstractSpanFactory;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;


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

        /*TextView tv = (TextView) findViewById(R.id.tv_test);
        MultiSpannableString spannableString = new MultiSpannableString("测试测试");
        UnderlineSpan span = new UnderlineSpan();
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color
                .red));
        spannableString.setMultiSpans(0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE, span,
                strikethroughSpan, colorSpan);
        spannableString.setSpan(ForegroundColorSpan.wrap(colorSpan), 2, 3, Spanned
                .SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);*/
        mPanel.setStateChange(new EditorPanel.onPanelStateChange() {
            @Override
            public void onStateChanged(BasePanelEvent state) {
                if (state instanceof FontChangeEvent) {
                    onFontEVent(state.isSelected);
                } else if (state instanceof ParagraphChangeEvent) {
                    onParagraphEvent((ParagraphChangeEvent)state);
                }
            }
        });
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
        SpanModel model = new SpanModel(pType);
        model.code = paramManager.getParamCode(pType);
        model.mSpans = spanFactory.createSpan(model.code);
        mEditor.getCurIndexModel().setParagraphSpan(model);
    }

    private void onFontEVent(boolean isShow) {
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
            KeyboardUtil.closeKeyboard(MainActivity.this);
        }
    }

}
