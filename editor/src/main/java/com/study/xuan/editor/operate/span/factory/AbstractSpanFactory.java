package com.study.xuan.editor.operate.span.factory;

import com.study.xuan.editor.common.Const;

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
    private IUpdateAppearanceFactory updateFactory;

    /**
     * 参数以1010101|20101010|301010形式存在
     * 1开头表示char类型
     * 2开头表示paragraph类型
     * 3开头表示update类型
     */
    @Override
    public List<Object> createSpan(String spanCode) {
        List<Object> spans = new ArrayList<>();
        String[] codes = spanCode.split("\\|");
        for (String code : codes) {
            if (code.startsWith(Const.SPAN_TYPE_FONT)) {
                createCharacterSpan(code, spans);
            } else if (code.startsWith(Const.SPAN_TYPE_PARAGRAPH)) {
                createParagraphSpan(code, spans);
            }
        }
        return spans;
    }

    @Override
    public void createCharacterSpan(String code, List<Object> spans) {
        createCharacterFactory();
        spans.addAll(charFactory.createCharSpans(code));
    }

    @Override
    public void createParagraphSpan(String code, List<Object> spans) {
        createParagraphFactory();
        spans.add(paragraphFactory.createParagraphSpans(code));
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
        if (updateFactory == null) {
            updateFactory = new UpdateFatory();
        }
        return updateFactory;
    }

}
