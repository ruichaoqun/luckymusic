package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
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

    public abstract void initCustomView();

    public abstract void onBottomSheetDismiss();

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

    public Activity getActivity() {
        Context context = getContext();
        int i2 = 20;
        while (context instanceof ContextWrapper) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return null;
            }
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
            i2 = i3;
        }
        return null;
    }
}