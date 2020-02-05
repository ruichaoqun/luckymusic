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
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;

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

    public void setContentView(View view) {
//        ((ViewGroup) view).getChildAt(0).setBackgroundDrawable(ad.b(getBackgroundColor(), NeteaseMusicUtils.a((int) R.dimen.f9if)));
        super.setContentView(view);
    }

    /* access modifiers changed from: protected */
    public int getBackgroundColor() {
        return ResourceRouter.getInstance().getPopupBackgroundColor();
    }

    public void setDiaglogView() {
        this.mDialogView = (ClosableSlidingLayout) View.inflate(getContext(), R.layout.dialog_base_bottom_sheet,  null);
        this.mDialogView.swipeable = true;
        this.mDialogView.setSlideListener(new ClosableSlidingLayout.SlideListener() {
            public void onClosed() {
                BaseBottomSheet.this.dismiss();
            }

            public void onOpened() {
            }
        });
        this.mDialogView.getLocationOnScreen(new int[2]);
        this.mDialogView.setCollapsible(false);
        initCustomView();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCanceledOnTouchOutside(true);
        super.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                BaseBottomSheet.this.onBottomSheetDismiss();
            }
        });
        super.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                BaseBottomSheet.this.onBottomSheetShow();
            }
        });
        setDiaglogView();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 80;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
    }

    /* compiled from: ProGuard */
    public abstract class BaseBottomSheetListViewAdapter<T> extends BaseAdapter {
        protected Context context;
        protected ArrayList<T> mList = new ArrayList<>();

        public boolean areAllItemsEnabled() {
            return false;
        }

        public BaseBottomSheetListViewAdapter(Context context2) {
            this.context = context2;
        }

        public int getCount() {
            if (this.mList != null) {
                return this.mList.size();
            }
            return 0;
        }

        public T getItem(int i) {
            if (this.mList == null || i >= this.mList.size()) {
                return null;
            }
            return this.mList.get(i);
        }

        public long getItemId(int i) {
            return 0;
        }

        public ArrayList<T> getList() {
            return this.mList;
        }

        public void setList(ArrayList<T> arrayList) {
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if (this.mList.size() != 0) {
                this.mList.clear();
            }
            this.mList.addAll(arrayList);
            notifyDataSetChanged();
        }

        public void clear() {
            this.mList.clear();
            notifyDataSetChanged();
        }
    }
}