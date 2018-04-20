package com.study.xuan.editor.widget.panel;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.study.xuan.editor.R;
import com.study.xuan.editor.model.panel.LinkModel;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Author : xuan.
 * Date : 18-3-31.
 * Description : link dialog
 */
public class LinkDialogFragment extends DialogFragment {
    private EditText etName;
    private EditText etLink;
    private TextView tvCancel;
    private TextView tvSure;

    public static LinkDialogFragment newInstance() {
        Bundle args = new Bundle();
        LinkDialogFragment fragment = new LinkDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.link_dialog, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //window.setLayout(MATCH_PARENT, WRAP_CONTENT);
        }
        initView(root);
        initEvent();
        return root;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }


    private void initEvent() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String link = etLink.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(link)) {
                    Toast.makeText(getActivity(), "请输入完整参数", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (onSureClickListener != null) {
                        onSureClickListener.onSure(new LinkModel(name, link));
                    }
                    dismiss();
                }

            }
        });
    }

    private void initView(View root) {
        etName = root.findViewById(R.id.et_link_name);
        etLink = root.findViewById(R.id.et_link);
        tvCancel = root.findViewById(R.id.tv_link_cancel);
        tvSure = root.findViewById(R.id.tv_link_sure);
    }

    public void setData(String name, String url) {
        etName.setText(name);
        etLink.setText(url);
    }

    public interface onSureClickListener {
        void onSure(LinkModel linkModel);
    }

    private onSureClickListener onSureClickListener;

    public void setOnSureClickListener(LinkDialogFragment.onSureClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
    }
}
