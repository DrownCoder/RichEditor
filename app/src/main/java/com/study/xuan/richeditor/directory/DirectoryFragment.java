package com.study.xuan.richeditor.directory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.xuan.richeditor.R;

import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/4/22.
 * Description :目录
 */

public class DirectoryFragment extends Fragment implements IDirectoryContract.IDirectoryView {
    private IDirectoryContract.IDirectoryPresent mPresent;
    private RecyclerView mRcy;
    private DirectoryAdapter mAdapter;

    public static DirectoryFragment newInstance() {
        Bundle args = new Bundle();
        DirectoryFragment fragment = new DirectoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_directory, container, false);
        mRcy = root.findViewById(R.id.rcy_directory);
        mRcy.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DirectoryAdapter(getActivity(), null);
        mRcy.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void setPresenter(IDirectoryContract.IDirectoryPresent presenter) {
        mPresent = presenter;
    }

    @Override
    public void updateView(List<IndexRoot> source) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.updateSource(source);
    }
}
