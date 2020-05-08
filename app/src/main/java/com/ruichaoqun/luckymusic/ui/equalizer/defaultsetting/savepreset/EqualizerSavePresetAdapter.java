package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020/5/8 9:36
 * description:
 */
public class EqualizerSavePresetAdapter extends BaseQuickAdapter<CustomEqBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public EqualizerSavePresetAdapter(List<CustomEqBean> data) {
        super(R.layout.item_adapter_equalizer_save_preset_update,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CustomEqBean item) {
        helper.setText(R.id.tv_title,item.getEqTitle());
    }
}
