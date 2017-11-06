package com.study.xuan.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

import static com.study.xuan.richeditor.RichAdapter.TYPE_EDIT;
import static com.study.xuan.richeditor.RichAdapter.TYPE_IMG;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT_HINT = "这本书的内容、作者、文笔…给你留下了怎样的印象？是否值得推荐给其他书友？写下你的书评吧~";
    private ImageView mIvAdd;
    private RecyclerView mRcy;
    private RichAdapter mAdapter;
    private LinearLayoutManager manager;
    private List<RichModel> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mRcy = (RecyclerView) findViewById(R.id.rcy);
        manager = new LinearLayoutManager(this);
        mRcy.setLayoutManager(manager);
        mDatas = new LinkedList<>();
        mDatas.add(new RichModel(TYPE_EDIT, "", false, DEFAULT_HINT));
        mAdapter = new RichAdapter(mDatas, this);
        mRcy.setAdapter(mAdapter);
        initEvent();
    }

    private void initEvent() {
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto("");
            }
        });
        mAdapter.setmOnScollIndex(new RichAdapter.onScrollIndex() {
            @Override
            public void scroll(int pos) {
                mRcy.scrollToPosition(pos);
            }
        });
    }

    /**
     * 添加图片
     */
    private void addPhoto(String newFile) {
        if (mDatas != null && mAdapter != null) {
            if (mDatas.size() == 1) {
                if (mDatas.get(0).source.length() == 0) {
                    mDatas.remove(0);
                    mAdapter.index = 0;
                }
            }
            removeEmptyLine();
            if (mDatas.size() == 0 || mAdapter.index == (mDatas.size() - 1)) {//无文本或者图片在末尾添加
                mDatas.add(new RichModel(TYPE_IMG, newFile, false, ""));
                mDatas.add(new RichModel(TYPE_EDIT, "", false, ""));
                mAdapter.index = mDatas.size() - 2;
            } else {//图片在文中添加
                mDatas.add(mAdapter.index + 1, new RichModel(TYPE_IMG, newFile, false, ""));
                mAdapter.index++;
            }
            mAdapter.notifyDataChanged();
        }
    }

    /**
     * 去除要增加图片上面的一行空行，多行只删除上面一行
     */
    private void removeEmptyLine() {
        if (mDatas.size() > 0 & mAdapter.index < mDatas.size()) {
            //空行表示长度为空，或者默认的一个空占位符
            if (mDatas.get(mAdapter.index).source.length() == 0 ||
                    TextUtils.equals(mDatas.get(mAdapter.index).source," ")) {
                mDatas.remove(mAdapter.index);
                mAdapter.index--;
            }
        }
    }
}
