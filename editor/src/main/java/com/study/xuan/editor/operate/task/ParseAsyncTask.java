package com.study.xuan.editor.operate.task;

import android.os.AsyncTask;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.operate.parse.MarkDownParser;
import com.study.xuan.editor.operate.parse.Parser;
import com.study.xuan.editor.util.RichLog;
import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : 异步转换markdown或者html
 */
public class ParseAsyncTask extends AsyncTask<List<RichModel>, Integer, String> {
    private List<RichModel> data;
    private int parseType;

    public ParseAsyncTask() {
    }

    public void setParseType(int parseType) {
        this.parseType = parseType;
    }

    @Override
    protected String doInBackground(List<RichModel>[] lists) {
        data = lists[0];
        Parser parser;
        switch (parseType) {
            case Const.MARKDOWN_PARSE_TYPE:
                parser = new MarkDownParser();
                break;
            default:
                parser = new MarkDownParser();
                break;
        }
        String content = parser.toString(data);
        RichLog.log(content);
        return content;
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
    }
}
