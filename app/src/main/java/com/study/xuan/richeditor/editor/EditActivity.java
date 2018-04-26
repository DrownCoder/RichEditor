package com.study.xuan.richeditor.editor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.study.xuan.editor.callback.onEditorEvent;
import com.study.xuan.editor.common.Const;
import com.study.xuan.editor.model.RichModel;
import com.study.xuan.editor.model.SelectionInfo;
import com.study.xuan.editor.model.SpanModel;
import com.study.xuan.editor.model.panel.state.ParagraphChangeEvent;
import com.study.xuan.editor.operate.helper.RichModelHelper;
import com.study.xuan.editor.operate.param.IParamManger;
import com.study.xuan.editor.operate.font.FontParam;
import com.study.xuan.editor.operate.RichBuilder;
import com.study.xuan.editor.operate.span.factory.IAbstractSpanFactory;
import com.study.xuan.editor.util.DensityUtil;
import com.study.xuan.editor.widget.RichEditor;
import com.study.xuan.editor.widget.panel.EditorPanelAlpha;
import com.study.xuan.editor.widget.panel.IPanel;
import com.study.xuan.editor.widget.panel.onPanelStateChange;
import com.study.xuan.richeditor.R;
import com.study.xuan.richeditor.app.BaseActivity;
import com.study.xuan.richeditor.task.ParseAsyncTask;
import com.study.xuan.richeditor.directory.DirectoryFragment;
import com.study.xuan.richeditor.directory.DirectoryPresenter;
import com.study.xuan.richeditor.directory.IDirectoryContract;
import com.study.xuan.richeditor.detail.DetailActivity;
import com.study.xuan.richeditor.model.RichEvent;
import com.study.xuan.richeditor.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_FONT;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_LINK;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PANEL;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PARAGRAPH;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_PHOTOPICKER;
import static com.study.xuan.editor.widget.panel.PanelBuilder.TYPE_SAVE;


public class EditActivity extends BaseActivity {
    TextView mTvSubmit;
    RichEditor mEditor;
    EditorPanelAlpha mPanel;
    FrameLayout mLeftContainer;
    IParamManger paramManager;
    IAbstractSpanFactory spanFactory;
    IPanel panel;
    ParseAsyncTask mTask;

    private DirectoryFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        long i = System.currentTimeMillis();
        Log.i("-------", (int) (i + 1000000000) + "");
        mEditor = (RichEditor) findViewById(R.id.editor);
        mPanel = (EditorPanelAlpha) findViewById(R.id.panel);
        mLeftContainer = findViewById(R.id.fl_left);
        //mTvSubmit = findViewById(R.id.tv_submit);

        fragment = DirectoryFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getFragmentManager(), fragment, R.id.fl_left);

        paramManager = RichBuilder.getInstance().getManger();
        spanFactory = RichBuilder.getInstance().getFactory();
        panel = RichBuilder.getInstance().getPanelBuilder();
        panel.setStateChange(new onPanelStateChange() {
            @Override
            public void onStateChanged() {
                switch (panel.getType()) {
                    case TYPE_FONT:
                        onFontEvent(panel.getFontParam());
                        break;
                    case TYPE_LINK:
                        onLinkEvent(panel.getFontParam());
                        break;
                    case TYPE_PARAGRAPH:
                        onParagraphEvent(panel.getParagraphType());
                        break;
                    case TYPE_PANEL:
                        onPanelEvent(panel.isShow());
                        break;
                    case TYPE_PHOTOPICKER:
                        onPhotoEvent();
                        break;
                    case TYPE_SAVE:
                        onSaveEvent();
                        break;
                }
            }
        });
        initEvent();

       /* mPanel.setStateChange(new EditorPanel.onPanelStateChange() {
            @Override
            public void onStateChanged(BasePanelEvent state) {
                if (state instanceof FontChangeEvent) {
                    onFontEvent(state.isSelected);
                } else if (state instanceof ParagraphChangeEvent) {
                    onParagraphEvent((ParagraphChangeEvent)state);
                } else if (state instanceof LinkChangeEvent) {
                    onLinkEvent((LinkChangeEvent) state);
                }
            }
        });*/
//        mTvSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseAsyncTask saveTask = new ParseAsyncTask(mEditor.getData());
//                saveTask.execute(Const.MARKDOWN_PARSE_TYPE);
//            }
//        });
    }

    private void onSaveEvent() {
        mTask = new ParseAsyncTask(EditActivity.this);
        mTask.setParseType(Const.MARKDOWN_PARSE_TYPE);
        mTask.execute(mEditor.getData());
    }

    private void initEvent() {
        mEditor.setOnEditorEvent(onEditorEvent);
    }

    private onEditorEvent onEditorEvent = new onEditorEvent() {
        @Override
        public void onChange(List<RichModel> data) {
            IDirectoryContract.IDirectoryPresent directoryPresent = new DirectoryPresenter(fragment);
            directoryPresent.updateDirectory(data);
        }
    };

    private void onPhotoEvent() {
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    private void onPanelEvent(boolean show) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mEditor.getLayoutParams();
        if (show) {
            params.bottomMargin = DensityUtil.dp2px(this, 100);
        } else {
            params.bottomMargin = DensityUtil.dp2px(this, 50);
        }
    }

    /**
     * 链接事件
     */
    /*private void onLinkEvent(LinkChangeEvent state) {
        SpanModel linkModel = new SpanModel(state.linkParam);
        linkModel.code = state.linkParam.getCharCodes();
        linkModel.mSpans = spanFactory.createSpan(linkModel.code);

        mEditor.getCurIndexModel().addSpanModel(state.title, linkModel);
        mEditor.notifyEvent();

        SpanModel spanModel = new SpanModel(paramManager.createNewParam());
        spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
        spanModel.mSpans = spanFactory.createSpan(spanModel.code);
        mEditor.getCurIndexModel().setNewSpan(spanModel);
    }*/
    private void onLinkEvent(FontParam param) {
        SpanModel linkModel = new SpanModel(param);
        linkModel.code = param.getCharCodes();
        linkModel.mSpans = spanFactory.createSpan(linkModel.code);

        mEditor.insertSpan(param.name, linkModel);
        mEditor.notifyEvent();
        RichBuilder.getInstance().reset();

        /*SpanModel spanModel = new SpanModel(paramManager.createNewParam());
        spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
        spanModel.mSpans = spanFactory.createSpan(spanModel.code);
        mEditor.getCurIndexModel().setNewSpan(spanModel);*/
    }

    /**
     * 段落样式改变事件
     */
    private void onParagraphEvent(int pType) {
        initParagraphSpan(pType);
        mEditor.notifyEvent();
    }

    private void onParagraphEvent(ParagraphChangeEvent state) {
        if (!state.isSelected) {
            mEditor.getCurIndexModel().setNoParagraphSpan();
            mEditor.notifyEvent();
            return;
        }
        initParagraphSpan(state.pType);
        mEditor.notifyEvent();
    }

    private void initParagraphSpan(int pType) {
        if (pType != Const.PARAGRAPH_NONE) {
            SpanModel model = new SpanModel(pType);
            model.code = paramManager.getParamCode(pType);
            model.mSpans = spanFactory.createSpan(model.code);
            mEditor.getCurIndexModel().setParagraphSpan(model);
        } else {
            mEditor.getCurIndexModel().setNoParagraphSpan();
        }
    }

    /**
     * 字体样式改变事件
     */
    /*private void onFontEvent(boolean isShow) {
        if (!isShow) {
            if (paramManager.needNewSpan(mPanel.getFontParams())) {//需要新生产span样式
                SpanModel spanModel = new SpanModel(paramManager.createNewParam());
                spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
                spanModel.mSpans = spanFactory.createSpan(spanModel.code);
                mEditor.getCurIndexModel().setNewSpan(spanModel);
            } else {
                mEditor.getCurIndexModel().setNoNewSpan();
            }
        }else{
            KeyboardUtil.closeKeyboard(this);
        }
    }*/
    private void onFontEvent(FontParam param) {
        if (checkEditorSelectStatus()) {
            //当前是选中状态
            final SelectionInfo info = mEditor.getSelectionInfo();
            if (info == null) {
                return;
            }
            //清楚选中状态样式
            int index = RichModelHelper.cleanBetweenArea(mEditor.getCurIndexModel().getSpanList(), info.startIndex, info.endIndex);
            if (param.isFontParamValid()) {
                //插入新样式
                SpanModel spanModel = new SpanModel(paramManager.cloneParam(param));
                spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
                spanModel.mSpans = spanFactory.createSpan(spanModel.code);
                spanModel.start = info.startIndex;
                spanModel.end = info.endIndex;
                mEditor.getCurIndexModel().setNewSpan(spanModel);
                mEditor.getCurIndexModel().getSpanList().add(index, spanModel);
            }
            mEditor.notifyEvent();
            mEditor.getCurEditText().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEditor.getCurEditText().setSelection(info.startIndex, info.endIndex);
                }
            }, 100);
            return;
        }
        if (paramManager.needNewSpan(param)) {//需要新生产span样式
            SpanModel spanModel = new SpanModel(paramManager.createNewParam());
            spanModel.code = paramManager.getParamCode(spanModel.paragraphType);
            spanModel.mSpans = spanFactory.createSpan(spanModel.code);
            mEditor.getCurIndexModel().setNewSpan(spanModel);
        } else {
            mEditor.getCurIndexModel().setNoNewSpan();
        }
        mEditor.saveInfo();
        mEditor.notifyEvent();
    }

    /**
     * 编辑器处于选中状态
     */
    private boolean checkEditorSelectStatus() {
        EditText etCur = mEditor.getCurEditText();
        if (etCur.getSelectionStart() != etCur.getSelectionEnd()) {
            return true;
        }
        return false;
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
