package com.study.xuan.richeditor.db;

import android.app.Activity;
import android.os.AsyncTask;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.operate.parse.MarkDownParser;
import com.study.xuan.editor.operate.parse.Parser;
import com.study.xuan.editor.util.RichLog;
import com.study.xuan.richeditor.model.RichEvent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : 异步转换markdown或者html
 */
public class ParseAsyncTask extends AsyncTask<List<RichModel>, Integer, Void> {
    private List<RichModel> data;
    private final WeakReference<Activity> mContext;
    private int parseType;

    public ParseAsyncTask(Activity mContext) {
        this.mContext = new WeakReference<>(mContext);
    }

    public void setParseType(int parseType) {
        this.parseType = parseType;
    }

    @Override
    protected Void doInBackground(List<RichModel>[] lists) {
        if (mContext.get() == null) {
            return null;
        }
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
        NoteDBHelper helper = new NoteDBHelper(mContext.get());
        helper.insert(content);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        EventBus.getDefault().post(new RichEvent(RichEvent.SAVE_SUCCESS));
    }
}
