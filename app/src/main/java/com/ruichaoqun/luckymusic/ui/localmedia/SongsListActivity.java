package com.ruichaoqun.luckymusic.ui.localmedia;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.media.session.MediaControllerCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.databinding.SongsListActivityBinding;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs.LocalMediaAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020-2-11 16:55:56
 * description:SongsListActivity
 */
public class SongsListActivity extends BaseMVPActivity<SongsListContact.Presenter> implements SongsListContact.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    private final static String INTENT_EXTRA_ID = "extra_id";
    private final static String INTENT_EXTRA_TYPE = "extra_type";
    private final static String INTENT_EXTRA_TITLE = "extra_title";

    private String id;
    private String type;
    private String title;
    private LocalMediaAdapter mSongsAdapter;
    private List<SongBean> mSongBeanList = new ArrayList<>();
    private SongsListActivityBinding mBinding;

    public static void launchFrom(Activity activity, String id, String type, String extra_title) {
        Intent intent = new Intent(activity, SongsListActivity.class);
        intent.putExtra(INTENT_EXTRA_ID, id);
        intent.putExtra(INTENT_EXTRA_TYPE, type);
        intent.putExtra(INTENT_EXTRA_TITLE, extra_title);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.songs_list_activity;
    }

    @Override
    protected View getContentView() {
        mBinding = SongsListActivityBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initParams() {
        this.id = getIntent().getStringExtra(INTENT_EXTRA_ID);
        this.type = getIntent().getStringExtra(INTENT_EXTRA_TYPE);
        this.title = getIntent().getStringExtra(INTENT_EXTRA_TITLE);
    }

    @Override
    protected void initView() {
        mBinding.rlPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 播放全部
            }
        });

        mBinding.tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 多选
            }
        });

        mBinding.rvSongList.setLayoutManager(new LinearLayoutManager(this));
        mSongsAdapter = new LocalMediaAdapter(R.layout.item_adapter_local_media, mSongBeanList);
        mSongsAdapter.setEmptyView(R.layout.layout_loading, mBinding.rvSongList);
        mBinding.rvSongList.setAdapter(mSongsAdapter);
        mSongsAdapter.setOnItemClickListener(this);
        mSongsAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initData() {
        setTitle(this.title);
        mPresenter.getSongsByType(type, id);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mControllerCompat != null) {
            mControllerCompat.getTransportControls().playFromMediaId(new MediaID(type, mSongBeanList.get(position).getId()).asString(), null);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadSongsSuccess(List<SongBean> list) {
        mSongBeanList.addAll(list);
        mSongsAdapter.notifyDataSetChanged();
        this.mBinding.tvCount.setText(String.format(getString(R.string.total_count), list.size()));
    }

    @Override
    protected void showMiniPlayerBarStub(boolean show) {
        super.showMiniPlayerBarStub(show);
        mSongsAdapter.showMiniPlayerBarStub(this,show);
    }
}
