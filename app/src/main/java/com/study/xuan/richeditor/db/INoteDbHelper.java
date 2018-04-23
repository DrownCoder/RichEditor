package com.study.xuan.richeditor.db;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/23.
 * Description :input the description of this file
 */

public interface INoteDbHelper<T> {
    void insert(String content); //保存数据

    List<T> queryAll(); //根据类名（表名）查找所有的数据

    T queryById(int id);//通过id查找对应的数据

    void delete(int id); //删除对应的数据;

    void deleteAll(); // 删除集合中所有的数据
}
