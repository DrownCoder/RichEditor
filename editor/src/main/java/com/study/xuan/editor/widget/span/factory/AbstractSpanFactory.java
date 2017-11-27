package com.study.xuan.editor.widget.span.factory;

import com.study.xuan.editor.model.SpanModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/23.
 * Description :根据FontParam参数生成不同的Span类型
 */

public class AbstractSpanFactory implements IAbstractSpanFactory {
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
        String spanCode = spanModel.param.getParamCodes();
        List<Object> spans = new ArrayList<>();
        String[] codes = spanCode.split("\\|");
        for (String code : codes) {
            if (code.startsWith("1")) {
                createCharacterFactory();
                spans.addAll(charFactory.createCharSpans(code));
            } else if (code.startsWith("2")) {
                createParagraphFactory();
                spans.addAll(paragraphFactory.createParagraphSpans(code));
            } else if (code.startsWith("3")) {
                createUpdateFactory();
                spans.addAll(updateFatory.createUpdateSpans(code));
            }
        }
        spanModel.mSpans = spans;
        return spans;
    }

    @Override
    public ICharacterStyleFactory createCharacterFactory() {
        if (charFactory == null) {
            charFactory = new CharactorFactory();
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
