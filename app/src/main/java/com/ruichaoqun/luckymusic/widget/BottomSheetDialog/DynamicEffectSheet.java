package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.app.Activity;
import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMediaBrowserActivity;
import com.ruichaoqun.luckymusic.ui.PlayerActivity;

import java.util.Arrays;
import java.util.List;

public class DynamicEffectSheet extends BaseBottomSheet{
    private static final float rate = 0.521f;
    private BottomSheetRecyclerView mRvEffectList;
    private TextView mTvCurrentEffectName;
    private int mEffectType;
    private DynamicEffectAdapter mDynamicEffectAdapter;


    public DynamicEffectSheet(Context context, int themeResId,int effectType) {
        super(context, themeResId);
        this.mEffectType = effectType;
    }

    @Override
    public void initCustomView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dynamic_effect,  null);
        this.mDialogView.addView(inflate);
        setContentView(this.mDialogView);
        this.mTvCurrentEffectName = inflate.findViewById(R.id.tv_current_effect);
        this.mRvEffectList = inflate.findViewById(R.id.rv_effect_list);
        initData();
    }

    private void initData() {
        String[] strings = getContext().getResources().getStringArray(R.array.dynamic_effect_list);
        List<String> list = Arrays.asList(strings);
        this.mDynamicEffectAdapter = new DynamicEffectAdapter(R.layout.item_adapter_dynamic_effect,list);
        this.mRvEffectList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRvEffectList.setScreenHeigtRate(rate);
        this.mRvEffectList.setAdapter(this.mDynamicEffectAdapter);
        this.mTvCurrentEffectName.setText(list.get(mEffectType));
        this.mDynamicEffectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position != mEffectType){
                    Activity activity = getActivity();
                    if(activity != null && activity instanceof PlayerActivity && !activity.isFinishing()){
                        ((PlayerActivity)activity).setEffectView(position);
                    }
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onBottomSheetDismiss() {
    }

    @Override
    public void onBottomSheetShow() {

    }

    public static DynamicEffectSheet showDynamicEffectSheet(Context context, int type) {
        DynamicEffectSheet dynamicEffectSheet = new DynamicEffectSheet(context, R.style.f3, type);
        dynamicEffectSheet.show();
        return dynamicEffectSheet;
    }

    private class DynamicEffectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private int color;

        public DynamicEffectAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper,String item) {
            helper.setText(R.id.tv_name,item);
        }
    }
}
