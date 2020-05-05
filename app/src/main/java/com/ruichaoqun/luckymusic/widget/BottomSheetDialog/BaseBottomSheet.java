package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import androidx.annotation.StyleRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.utils.drawhelper.ColorDrawableUtils;

import java.util.ArrayList;

public abstract class BaseBottomSheet extends Dialog implements DialogInterface {
    protected ClosableSlidingLayout mDialogView;

    /* access modifiers changed from: protected */
    public abstract void initCustomView();

    /* access modifiers changed from: protected */
    public abstract void onBottomSheetDismiss();

    /* access modifiers changed from: protected */
    public abstract void onBottomSheetShow();

    public BaseBottomSheet(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(View view) {
        ((ViewGroup) view).getChildAt(0).setBackgroundDrawable(ColorDrawableUtils.getTopCornerColorDrawable(getBackgroundColor(), UiUtils.dp2px(15)));
        super.setContentView(view);
    }

    public int getBackgroundColor() {
        return ResourceRouter.getInstance().getPopupBackgroundColor();
    }

    public void setDiaglogView() {
        this.mDialogView = (ClosableSlidingLayout) View.inflate(getContext(), R.layout.dialog_base_bottom_sheet,  null);
        this.mDialogView.swipeable = true;
        this.mDialogView.setSlideListener(new ClosableSlidingLayout.SlideListener() {
            @Override
            public void onClosed() {
                BaseBottomSheet.this.dismiss();
            }

            @Override
            public void onOpened() {
            }
        });
        this.mDialogView.getLocationOnScreen(new int[2]);
        this.mDialogView.setCollapsible(false);
        initCustomView();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCanceledOnTouchOutside(true);
        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                BaseBottomSheet.this.onBottomSheetDismiss();
            }
        });
        setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BaseBottomSheet.this.onBottomSheetShow();
            }
        });
        setDiaglogView();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 80;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
    }
}