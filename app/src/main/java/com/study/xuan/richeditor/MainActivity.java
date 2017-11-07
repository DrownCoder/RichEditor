package com.study.xuan.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.study.xuan.richeditor.RichAdapter.TYPE_EDIT;
import static com.study.xuan.richeditor.RichAdapter.TYPE_IMG;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT_HINT = "默认文字";
    private ImageView mIvAdd;
    private RecyclerView mRcy;
    private RichAdapter mAdapter;
    private LinearLayoutManager manager;
    private List<RichModel> mDatas;

    private View mHeader;

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
        mHeader = LayoutInflater.from(this).inflate(R.layout.head_item, null);
        mAdapter.addHeaderView(mHeader);
        mRcy.setAdapter(mAdapter);
        initEvent();
    }

    private void initEvent() {

        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                list.add("");
                addPhoto(list);
            }
        });
        mAdapter.setOnScollIndex(new RichAdapter.onScrollIndex() {
            @Override
            public void scroll(int pos) {
                mRcy.scrollToPosition(pos);
            }
        });
    }

    /**
     * 添加图片
     */
    private void addPhoto(ArrayList<String> photos) {
        if (mDatas != null && mAdapter != null) {
            //首行空行删除，直接增加图片+行
            if (mDatas.size() == 1) {
                if (mDatas.get(0).source.length() == 0) {
                    mDatas.remove(0);
                    mAdapter.index = 0;
                }
            }
            removeEmptyLine();
            if (mDatas.size() == 0 || mAdapter.index == (mDatas.size() - 1)) {//无文本或者图片在末尾添加
                for (String newFile : photos) {
                    mDatas.add(new RichModel(TYPE_IMG, newFile, false, ""));
                }
                mDatas.add(new RichModel(TYPE_EDIT, "", false, ""));
                mAdapter.index = mDatas.size() - 2;
            } else {//图片在文中添加
                for (String newFile : photos) {
                    mDatas.add(mAdapter.index + 1, new RichModel(TYPE_IMG, newFile, false, ""));
                }
                mAdapter.index++;
            }
            mAdapter.notifyDataChanged();
            mRcy.scrollToPosition(mAdapter.index + 2);
        }
    }

    /**
     * 去除要增加图片上面的一行空行，多行只删除上面一行
     */
    private void removeEmptyLine() {
        if (mDatas.size() > 0 & mAdapter.index < mDatas.size()) {
            //空行表示长度为空，或者默认的一个空占位符
            if (mDatas.get(mAdapter.index).source.length() == 0 ||
                    TextUtils.equals(mDatas.get(mAdapter.index).source, " ")) {
                mDatas.remove(mAdapter.index);
                mAdapter.index--;
            }
        }
    }
}
