package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.EqualizerPresetBean;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;
import com.ruichaoqun.luckymusic.widget.EqualizerCurveView;

import java.util.List;

public class EqualizerPresetAdapter extends BaseQuickAdapter<EqualizerPresetBean, BaseViewHolder> {
    public EqualizerPresetAdapter(@Nullable List<EqualizerPresetBean> data) {
        super(R.layout.item_adapter_effect_preset, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EqualizerPresetBean item) {
        helper.setVisible(R.id.iv_checked, item.isChecked());
        helper.setText(R.id.tv_name,item.getTitle());
        int type = item.getType();
        if(type == 0){
            helper.setGone(R.id.tv_head, false);
            helper.setVisible(R.id.ecv_curve,false);
            helper.setGone(R.id.iv_profile,false);
        }else if(type == 1){
            helper.setGone(R.id.tv_head, mData.indexOf(item) == 1);
            helper.setText(R.id.tv_head,R.string.equalizer_activity_custom_define);
            helper.setVisible(R.id.ecv_curve,false);
            helper.setGone(R.id.iv_profile,true);
        }else {
            helper.setGone(R.id.tv_head, mData.get(mData.indexOf(item)-1).getType() != 2);
            helper.setText(R.id.tv_head,R.string.equalizer_activity_system_preset);
            helper.setVisible(R.id.ecv_curve,true);
            helper.setGone(R.id.iv_profile,false);
            AudioEffectJsonPackage.Eq eq = AudioEffectJsonPackage.Eq.getDefaultEqByIndex(item.getPresetIndex());
            ((EqualizerCurveView)helper.getView(R.id.ecv_curve)).setData(eq.getEqs());
            item.setmDatas(eq.getEqs());
        }
        helper.addOnClickListener(R.id.ll_select);
        helper.addOnClickListener(R.id.iv_profile);
    }

    public void setSelectedPosition(int position) {
        int targetInvisible = 0;
        for (int i = 0; i < getData().size(); i++) {
            if(getData().get(i).isChecked()){
                getData().get(i).setChecked(false);
                targetInvisible = i;
                break;
            }
        }
        getData().get(position).setChecked(true);
        notifyItemChanged(targetInvisible);
        notifyItemChanged(position);
    }
}
