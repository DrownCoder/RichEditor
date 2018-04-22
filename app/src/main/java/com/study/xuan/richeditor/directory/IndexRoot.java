package com.study.xuan.richeditor.directory;

/**
 * Author : xuan.
 * Date : 2018/4/22.
 * Description :目录
 */

public class IndexRoot {
    public String title;
    public int index;
    public int priority;

    public IndexRoot(String source, int i, int priority) {
        title = source;
        index = i;
        this.priority = priority;
    }
}
