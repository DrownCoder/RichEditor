package com.study.xuan.editor.widget.span.factory;

import com.study.xuan.editor.model.SpanModel;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2017/11/23.
 * Description :span抽象工厂，分为字符级别文本格式，段落级别文本格式，字符级别文本外观
 */

public interface IAbstractSpanFactory {
    public List<Object> createSpan(SpanModel spanModel);

    public void createCharacterSpan(List<Object> data);

    public void createParagraphSpan(List<Object> data);

    public ICharacterStyleFactory createCharacterFactory();

    public IParagraphFactory createParagraphFactory();

    public IUpdateAppearanceFactory createUpdateFactory();
}
