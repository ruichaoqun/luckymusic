package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.EqualizerPresetBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs.LocalMediaAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020-4-29 22:30:54
 * description:DefaultEffectActivity
 */
public class DefaultEffectActivity extends BaseMVPActivity<DefaultEffectContact.Presenter> implements DefaultEffectContact.View{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private EqualizerPresetAdapter mEqualizerPresetAdapter;
    private List<EqualizerPresetBean> mPresetBeans = new ArrayList<>();

    public static void launchFrom(Activity activity){
        activity.startActivity(new Intent(activity,DefaultEffectActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.default_effect_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        setTitle("预设");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEqualizerPresetAdapter = new EqualizerPresetAdapter(mPresetBeans);
        mEqualizerPresetAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(mEqualizerPresetAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.getPresetData();
    }

    @Override
    public boolean isNeedMiniPlayerBar() {
        return false;
    }

    @Override
    public void onLoadPresetDataSuccess(List<EqualizerPresetBean> data) {
        mPresetBeans.addAll(data);
        mEqualizerPresetAdapter.notifyDataSetChanged();
    }
}
