package com.study.xuan.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView mIvAdd;
    private RecyclerView mRcy;
    private LinearLayoutManager manager;

    private View mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mRcy = (RecyclerView) findViewById(R.id.rcy);
    }

}
