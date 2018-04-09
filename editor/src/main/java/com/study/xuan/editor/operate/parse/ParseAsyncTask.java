package com.study.xuan.editor.operate.parse;

import android.os.AsyncTask;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.util.RichLog;

import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : the file description
 */
public class ParseAsyncTask extends AsyncTask<Integer, Integer, String> {
    private List<RichModel> data;

    public ParseAsyncTask(List<RichModel> data) {
        this.data = data;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        int parseType = integers[0];
        Parser parser;
        switch (parseType) {
            case Const.GSON_PARSE_TYPE:
                parser = new MarkDownParser();
                break;
            default:
                parser = new MarkDownParser();
                break;
        }
        return parser.toString(data);
    }

    @Override
    protected void onPostExecute(String s) {
        RichLog.log(s);
        super.onPostExecute(s);
    }
}
