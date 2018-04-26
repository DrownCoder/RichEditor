package com.study.xuan.richeditor.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.xuan.editor.widget.RichShower;
import com.study.xuan.richeditor.R;
import com.study.xuan.richeditor.app.BaseActivity;
import com.study.xuan.richeditor.model.RichEvent;
import com.study.xuan.richeditor.task.TransFormerTask;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.study.xuan.richeditor.model.RichEvent.JUMP_DETAIL;
import static com.study.xuan.richeditor.model.RichEvent.TRANS_SUCCESS;

/**
 * Author : xuan.
 * Date : 2018/4/23.
 * Description :详情
 */

public class DetailActivity extends BaseActivity {
    private RichShower shower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        shower = findViewById(R.id.rs_show);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(RichEvent event) {
        switch (event.status) {
            case TRANS_SUCCESS:
                shower.setData(event.mData);
                break;
        }
    }

    @Subscribe(sticky = true)
    public void onBundleEvent(RichEvent event) {
        switch (event.status) {
            case JUMP_DETAIL:
                TransFormerTask task = new TransFormerTask();
                task.execute(event.content);
                break;
        }
    }
}
