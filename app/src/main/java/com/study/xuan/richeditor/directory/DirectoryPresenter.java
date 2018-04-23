package com.study.xuan.richeditor.directory;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SpanModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Author : xuan.
 * Date : 2018/4/22.
 * Description :input the description of this file
 */

public class DirectoryPresenter implements IDirectoryContract.IDirectoryPresent {
    private IDirectoryContract.IDirectoryView mFragment;

    public DirectoryPresenter(IDirectoryContract.IDirectoryView fragment) {
        this.mFragment = fragment;
    }

    @Override
    public void updateDirectory(List<RichModel> source) {
        List<IndexRoot> data = transformDirectory(source);
        mFragment.updateView(data);
    }

    private List<IndexRoot> transformDirectory(List<RichModel> source) {
        List<IndexRoot> data = new ArrayList<>();
        IndexRoot index;
        for (int i = 0; i < source.size(); i++) {
            RichModel item = source.get(i);
            if (item.isParagraphStyle) {
                SpanModel paModel = item.paragraphSpan;
                if (paModel.paragraphType != Const.PARAGRAPH_NONE && paModel.paragraphType != Const.PARAGRAPH_REFER) {
                    index = new IndexRoot(item.source, i, paModel.paragraphType);
                    data.add(index);
                }
            }
        }
        return data;
    }

    @Override
    public void start() {
        
    }
}
