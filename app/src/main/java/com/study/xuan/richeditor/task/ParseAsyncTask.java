package com.study.xuan.richeditor.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.operate.parse.MarkDownParser;
import com.study.xuan.editor.operate.parse.Parser;
import com.study.xuan.editor.util.RichLog;
import com.study.xuan.richeditor.db.NoteDBHelper;
import com.study.xuan.richeditor.model.RichEvent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Author : xuan.
 * Date : 18-4-9.
 * Description : 异步转换markdown或者html
 */
public class ParseAsyncTask extends AsyncTask<List<RichModel>, Integer, String> {
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
    protected String doInBackground(List<RichModel>[] lists) {
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
        return content;
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        RichEvent event = new RichEvent(RichEvent.SAVE_SUCCESS);
        event.setContent(content);
        EventBus.getDefault().post(event);
    }
}
