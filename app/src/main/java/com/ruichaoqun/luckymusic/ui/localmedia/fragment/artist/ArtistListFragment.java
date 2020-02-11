package com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist;

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
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.media.MediaControllerInterface;
import com.ruichaoqun.luckymusic.media.MediaDataType;
import com.ruichaoqun.luckymusic.ui.localmedia.SongsListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020/1/6 15:39
 * description:
 */
public class ArtistListFragment extends BaseLazyFragment<ArtistListPresenter> implements ArtistListContact.View, MediaControllerInterface, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ArtistListAdapter mArtistListAdapter;
    private List<ArtistBean> mArtistBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_media;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArtistListAdapter = new ArtistListAdapter(R.layout.item_adapter_artist, mArtistBeanList);
        mArtistListAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(mArtistListAdapter);
        mArtistListAdapter.setOnItemClickListener(this);
        mArtistListAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onLazyLoad() {
        mPresenter.getLocalArtist();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SongsListActivity.launchFrom(getActivity(),String.valueOf(mArtistBeanList.get(position).getId()),MediaDataType.TYPE_ARTIST,mArtistBeanList.get(position).getArtistName());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void updateMiniPlayerBarState(boolean isShow) {
        mArtistListAdapter.showMiniPlayerBarStub(getActivity(),isShow);
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        if (isAdded() && !TextUtils.isEmpty(metadata.getDescription().getMediaId())) {
            mArtistListAdapter.setCurrentArtist(metadata.getDescription().getSubtitle().toString());
            mArtistListAdapter.showMiniPlayerBarStub(getActivity(),true);
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
    public void onLoadArtistSuccess(List<ArtistBean> list) {
        mArtistBeanList.clear();
        mArtistBeanList.addAll(list);
        mArtistListAdapter.notifyDataSetChanged();
    }


}
