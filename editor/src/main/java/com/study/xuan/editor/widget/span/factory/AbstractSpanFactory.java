package com.study.xuan.editor.widget.span.factory;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.SpanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/23.
 * Description :根据FontParam参数生成不同的Span类型
 */

public class AbstractSpanFactory implements IAbstractSpanFactory {
    private SpanModel model;
    private ICharacterStyleFactory charFactory;
    private IParagraphFactory paragraphFactory;
    private IUpdateAppearanceFactory updateFatory;

    /**
     * 参数以1010101|20101010|301010形式存在
     * 1开头表示char类型
     * 2开头表示paragraph类型
     * 3开头表示update类型
     */
    @Override
    public List<Object> createSpan(SpanModel spanModel) {
        this.model = spanModel;
        List<Object> spans = new ArrayList<>();

        switch (spanModel.spanType) {
            case Const.SPAN_TYPE_FONT:
                createCharacterSpan(spans);
                break;
            case Const.SPAN_TYPE_PARAGRAPH:
                createParagraphSpan(spans);
                break;
        }
        spanModel.mSpans = spans;
        return spans;
    }

    @Override
    public void createCharacterSpan(List<Object> spans) {
        String spanCode = model.param.getParamCodes();
        String[] codes = spanCode.split("\\|");
        for (String code : codes) {
            if (code.startsWith("1")) {
                createCharacterFactory();
                spans.addAll(charFactory.createCharSpans(code));
            }
        }
    }

    @Override
    public void createParagraphSpan(List<Object> spans) {
        createParagraphFactory();
        spans.add(paragraphFactory.createParagraphSpans(model.paragraphType));
    }

    @Override
    public ICharacterStyleFactory createCharacterFactory() {
        if (charFactory == null) {
            charFactory = new CharacterFactory();
        }
        return charFactory;
    }

    @Override
    public IParagraphFactory createParagraphFactory() {
        if (paragraphFactory == null) {
            paragraphFactory = new ParagraphFactory();
        }
        return paragraphFactory;
    }

    @Override
    public IUpdateAppearanceFactory createUpdateFactory() {
        if (updateFatory == null) {
            updateFatory = new UpdateFatory();
        }
        return updateFatory;
    }

}
