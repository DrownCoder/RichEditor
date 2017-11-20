package com.study.xuan.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.xuan.editor.widget.span.ImageWrapperSpan;

public class MainActivity extends AppCompatActivity {
    private ImageView mIvAdd;
    private RecyclerView mRcy;
    private LinearLayoutManager manager;

    private View mHeader;
    private TextView mTv;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mIvAdd = (ImageView) findViewById(R.id.iv_add);
        //mRcy = (RecyclerView) findViewById(R.id.rcy);
        mTv = (TextView) findViewById(R.id.tv_test);
        mIv = (ImageView) findViewById(R.id.iv_test);
        SpannableString spannableString = new SpannableString
                ("《兰亭集》是一本由37首诗组成的诗集，关于《兰亭集》的来由是这样的：东晋穆帝永和九年（公元353" +
                        "年）的三月初三，当时任会稽内史、右军将军的王羲之邀请谢安、孙绰、孙统等四十一位文人雅士聚于会稽山阴（今浙江绍兴）兰亭修稧，曲水流觞，饮酒作诗。众人沉醉于酒香诗美的回味之时，有人提议不如将当日所做的三十七首诗，汇编成集，这便是《兰亭集》。这时众人又推王羲之写一篇序。王羲之酒意正浓，提笔在蚕纸上畅意挥毫，一气呵成。这就有了冠绝千古的《兰亭序》《兰亭集》是一本由37首诗组成的诗集，关于《兰亭集》的来由是这样的：东晋穆帝永和九年（公元353年）的三月初三，当时任会稽内史、右军将军的王羲之邀请谢安、孙绰、孙统等四十一位文人雅士聚于会稽山阴（今浙江绍兴）兰亭修稧，曲水流觞，饮酒作诗。众人沉醉于酒香诗美的回味之时，有人提议不如将当日所做的三十七首诗，汇编成集，这便是《兰亭集》。这时众人又推王羲之写一篇序。王羲之酒意正浓，提笔在蚕纸上畅意挥毫，一气呵成。这就有了冠绝千古的《兰亭序》《兰亭集》是一本由37首诗组成的诗集，关于《兰亭集》的来由是这样的：东晋穆帝永和九年（公元353年）的三月初三，当时任会稽内史、右军将军的王羲之邀请谢安、孙绰、孙统等四十一位文人雅士聚于会稽山阴（今浙江绍兴）兰亭修稧，曲水流觞，饮酒作诗。众人沉醉于酒香诗美的回味之时，有人提议不如将当日所做的三十七首诗，汇编成集，这便是《兰亭集》。这时众人又推王羲之写一篇序。王羲之酒意正浓，提笔在蚕纸上畅意挥毫，一气呵成。这就有了冠绝千古的《兰亭序》");
        spannableString.setSpan(new ImageWrapperSpan(mTv,100,100),10,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTv.setText(spannableString);
    }

}
