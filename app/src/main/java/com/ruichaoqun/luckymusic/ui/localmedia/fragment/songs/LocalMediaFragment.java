package com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.fragment.BaseLazyFragment;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.media.MediaControllerInterface;
import com.ruichaoqun.luckymusic.media.MediaDataType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020/1/6 15:39
 * description:
 */
public class LocalMediaFragment extends BaseLazyFragment<LocalMediaPresenter> implements LocalMediaContact.View,MediaControllerInterface, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LocalMediaAdapter mLocalMediaAdapter;
    private List<SongBean> mSongBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_media;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocalMediaAdapter = new LocalMediaAdapter(R.layout.item_adapter_local_media, mSongBeanList);
        mLocalMediaAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(mLocalMediaAdapter);
        mLocalMediaAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLazyLoad() {
        mPresenter.getLocalSongs();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (isAdded()) {
            MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
            if (controller != null) {
                controller.getTransportControls().playFromMediaId(new MediaID(MediaDataType.TYPE_SONG , mLocalMediaAdapter.getItem(position).getId()).asString(), null);
            }
        }
    }

    @Override
    public void updateMiniPlayerBarState(boolean isShow) {
        mLocalMediaAdapter.showMiniPlayerBarStub(getActivity(),isShow);
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        if (isAdded() && !TextUtils.isEmpty(metadata.getDescription().getMediaId())) {
            mLocalMediaAdapter.setMediaWithId(metadata.getDescription().getMediaId());
            mLocalMediaAdapter.showMiniPlayerBarStub(getActivity(),true);
        }
    }

    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {

    }

    @Override
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
        updateMiniPlayerBarState(queue.size() > 0);
    }

    @Override
    public void onLoadSongsSuccess(List<SongBean> list) {
        mSongBeanList.clear();
        mSongBeanList.addAll(list);
        mLocalMediaAdapter.notifyDataSetChanged();
    }
}
