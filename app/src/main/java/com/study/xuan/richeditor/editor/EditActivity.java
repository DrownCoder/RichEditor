package com.study.xuan.richeditor.editor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.study.xuan.editor.operate.param.IParamManger;
import com.study.xuan.editor.operate.RichBuilder;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.widget.RichEditor;
import com.study.xuan.editor.widget.panel.EditorPanelAlpha;
import com.study.xuan.editor.widget.panel.IPanel;
import com.study.xuan.richeditor.R;
import com.study.xuan.richeditor.app.BaseActivity;
import com.study.xuan.richeditor.directory.DirectoryFragment;
import com.study.xuan.richeditor.detail.DetailActivity;
import com.study.xuan.richeditor.model.RichEvent;
import com.study.xuan.richeditor.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;


public class EditActivity extends BaseActivity {
    TextView mTvSubmit;
    RichEditor mEditor;
    EditorPanelAlpha mPanel;
    FrameLayout mLeftContainer;

    private DirectoryFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mEditor = (RichEditor) findViewById(R.id.editor);
        mPanel = (EditorPanelAlpha) findViewById(R.id.panel);
        mLeftContainer = findViewById(R.id.fl_left);
        //mTvSubmit = findViewById(R.id.tv_submit);

        fragment = DirectoryFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getFragmentManager(), fragment, R.id.fl_left);


    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                mEditor.addPhoto(photos);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichBuilder.getInstance().destroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(RichEvent event) {
        switch (event.status) {
            case RichEvent.SAVE_SUCCESS:
                event.setStatus(RichEvent.JUMP_DETAIL);
                EventBus.getDefault().postSticky(event);
                Intent intent = new Intent(this, DetailActivity.class);
                startActivity(intent);
                break;
        }
    }
}
