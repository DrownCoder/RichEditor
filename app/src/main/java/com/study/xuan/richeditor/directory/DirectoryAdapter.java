package com.study.xuan.richeditor.directory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.xuan.richeditor.R;
import com.study.xuan.richeditor.utils.RichUtils;

import java.util.ArrayList;
import java.util.List;

import static com.study.xuan.editor.common.Const.PARAGRAPH_T1;
import static com.study.xuan.editor.common.Const.PARAGRAPH_T2;
import static com.study.xuan.editor.common.Const.PARAGRAPH_T3;
import static com.study.xuan.editor.common.Const.PARAGRAPH_T4;

/**
 * Author : xuan.
 * Date : 2018/4/22.
 * Description :目录Adapter
 */

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryVH> {
    private List<IndexRoot> data;
    private Context mContext;

    public DirectoryAdapter(Context context, List<IndexRoot> data) {
        this.mContext = context;
        this.data = data;
        if (data == null) {
            this.data = new ArrayList<>();
        }
    }

    @Override
    public DirectoryVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DirectoryVH(LayoutInflater.from(mContext).inflate(R.layout.item_directory, parent, false));
    }

    @Override
    public void onBindViewHolder(DirectoryVH holder, int position) {
        IndexRoot item = data.get(position);
        switch (item.priority) {
            case PARAGRAPH_T1:
                RichUtils.setTextStyle(mContext, holder.tvIndex, R.style.DirectoryFirst);
                break;
            case PARAGRAPH_T2:
                RichUtils.setTextStyle(mContext, holder.tvIndex, R.style.DirectorySecond);
                break;
            case PARAGRAPH_T3:
                RichUtils.setTextStyle(mContext, holder.tvIndex, R.style.DirectoryThird);
                break;
            case PARAGRAPH_T4:
                RichUtils.setTextStyle(mContext, holder.tvIndex, R.style.DirectoryFourth);
                break;
        }
        holder.tvIndex.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateSource(List<IndexRoot> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    class DirectoryVH extends RecyclerView.ViewHolder {
        TextView tvIndex;

        public DirectoryVH(View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_item_directory);
        }
    }

}
