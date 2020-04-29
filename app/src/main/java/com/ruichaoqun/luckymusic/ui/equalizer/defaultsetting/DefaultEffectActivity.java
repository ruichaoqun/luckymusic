package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import androidx.recyclerview.widget.RecyclerView;

import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
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
public class DefaultEffectActivity extends BaseMVPActivity<DefaultEffectContact.Presenter> {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LocalMediaAdapter mLocalMediaAdapter;
    private List<SongBean> mSongBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.default_effect_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isNeedMiniPlayerBar() {
        return false;
    }
}
