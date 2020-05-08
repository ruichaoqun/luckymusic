package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.R;

public class CustomEqActionSheet extends BaseBottomSheet {
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private TextView tvTitle;
    private String title;

    public CustomEqActionSheet(Context context, int themeResId, OnMenuItemClickListener onMenuItemClickListener) {
        super(context, themeResId);
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    public void initCustomView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom_eq, null);
        this.mDialogView.addView(inflate);
        tvTitle = inflate.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        inflate.findViewById(R.id.ll_edit).setOnClickListener(v -> {
            mOnMenuItemClickListener.onMenuItemClick(0);
            dismiss();
        });
        inflate.findViewById(R.id.ll_delete).setOnClickListener(v -> {
            mOnMenuItemClickListener.onMenuItemClick(1);
            dismiss();
        });
        setContentView(this.mDialogView);
    }

    @Override
    public void onBottomSheetDismiss() {

    }

    @Override
    public void onBottomSheetShow() {

    }

    public void setText(String text) {
        this.title = text;
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int type);
    }
}
