package com.study.xuan.editor.operate.sort;

/**
 * Author : xuan.
 * Date : 18-4-17.
 * Description : the file description
 */
public class SearchResult {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_BETWEEN = -1;
    public static final int CODE_NONE = -2;

    public int resultCode;
    public int resultIndex;
    public int resultParam;

    public SearchResult(int i) {
        resultCode = CODE_SUCCESS;
        resultIndex = i;
    }

    public SearchResult(int code, int i) {
        resultCode = code;
        resultIndex = i;
    }

    public SearchResult() {
        resultCode = CODE_NONE;
    }
}
