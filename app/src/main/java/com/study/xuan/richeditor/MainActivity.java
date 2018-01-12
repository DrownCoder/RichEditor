package com.study.xuan.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.model.panel.state.BasePanelEvent;
import com.study.xuan.editor.model.panel.state.FontChangeEvent;
import com.study.xuan.editor.model.panel.state.ParagraphChangeEvent;
import com.study.xuan.editor.operate.FontParamManager;
import com.study.xuan.editor.util.KeyboardUtil;
import com.study.xuan.editor.widget.EditorPanel;
import com.study.xuan.editor.widget.RichEditor;
import com.study.xuan.editor.widget.span.factory.AbstractSpanFactory;
import com.study.xuan.editor.widget.span.factory.IAbstractSpanFactory;


public class MainActivity extends AppCompatActivity {
    RichEditor mEditor;
    EditorPanel mPanel;
    FontParamManager paramManager;
    IAbstractSpanFactory spanFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditor = (RichEditor) findViewById(R.id.editor);
        mPanel = (EditorPanel) findViewById(R.id.panel);
        paramManager = new FontParamManager();
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
                    onParagraphEvent(((ParagraphChangeEvent) state).pType);
                }
            }
        });
    }

    private void onParagraphEvent(int pType) {
        switch (pType) {
            case Const.PARAGRAPH_REFER:
                //引用
                Log.i(Const.BASE_LOG, "引用");
                break;
        }

    }

    private void onFontEVent(boolean isShow) {
        if (!isShow) {
            if (paramManager.needNewSpan(mPanel.getFontParams())) {//需要新生产span样式
                SpanModel spanModel = new SpanModel(paramManager.createNewParam());
                spanFactory.createSpan(spanModel);
                mEditor.getCurIndexModel().setNewSpan(spanModel);
            } else {
                mEditor.getCurIndexModel().setNoNewSpan();
            }
        }else{
            KeyboardUtil.closeKeyboard(MainActivity.this);
        }
    }

}
