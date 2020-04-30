package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.EqualizerPresetBean;

import java.util.List;

public class EqualizerPresetAdapter extends BaseQuickAdapter<EqualizerPresetBean, BaseViewHolder> {
    public EqualizerPresetAdapter(@Nullable List<EqualizerPresetBean> data) {
        super(R.layout.item_adapter_effect_preset, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EqualizerPresetBean item) {
    }
}
