package com.study.xuan.richeditor.task;

import android.os.AsyncTask;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.operate.parse.MarkDownParser;
import com.study.xuan.editor.operate.parse.Parser;
import com.study.xuan.richeditor.model.RichEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/26.
 * Description :input the description of this file
 */

public class TransFormerTask extends AsyncTask<String, Integer, List<RichModel>> {
    private int transformType = Const.MARKDOWN_PARSE_TYPE;

    public void setTransformType(int transformType) {
        this.transformType = transformType;
    }

    @Override
    protected List<RichModel> doInBackground(String... str) {
        String content = str[0];
        Parser parser = null;
        switch (transformType) {
            case Const.MARKDOWN_PARSE_TYPE:
                parser = new MarkDownParser();
                break;
            default:
                parser = new MarkDownParser();
        }
        return parser.fromString(content);
    }

    @Override
    protected void onPostExecute(List<RichModel> richModels) {
        RichEvent event = new RichEvent(RichEvent.TRANS_SUCCESS);
        event.setData(richModels);
        EventBus.getDefault().post(event);
        super.onPostExecute(richModels);
    }
}
