package com.ruichaoqun.luckymusic.ui.theme.themecolor;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.utils.drawhelper.ColorDrawableUtils;

import java.util.List;

public class ThemeColorDetailAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private int currentColor;

    public ThemeColorDetailAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        Log.w("aaaa","|as");
        if(item == 0){
            helper.setImageResource(R.id.theme_color,R.drawable.ic_custom_color);
        }else{
            helper.setImageDrawable(R.id.theme_color,ColorDrawableUtils.getRoundCornerColorDrawable(item, UiUtils.dp2px(4.0f)));
        }
        helper.setVisible(R.id.iv_checked,item == currentColor? true:false);
        helper.addOnClickListener(R.id.theme_color);
    }
}
