package com.study.xuan.richeditor.directory;

import com.study.xuan.editor.model.RichModel;
import com.study.xuan.richeditor.BasePresenter;
import com.study.xuan.richeditor.BaseView;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/22.
 * Description :input the description of this file
 */

public interface IDirectoryContract {
    public interface IDirectoryView extends BaseView<IDirectoryPresent>{
        void updateView(List<IndexRoot> source);
    }

    public interface IDirectoryPresent extends BasePresenter{
        public void updateDirectory(List<RichModel> source);
    }
}
