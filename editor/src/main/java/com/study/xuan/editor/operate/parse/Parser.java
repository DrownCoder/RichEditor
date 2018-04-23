package com.study.xuan.editor.operate.parse;

import com.study.xuan.editor.model.RichModel;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : the file description
 */
public abstract class Parser {
    public abstract String toString(List<RichModel> data);

    public abstract List<RichModel> fromString(String t);
}
