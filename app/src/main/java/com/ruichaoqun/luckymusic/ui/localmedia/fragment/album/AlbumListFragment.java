package com.ruichaoqun.luckymusic.ui.localmedia.fragment.album;

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
import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
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
public class AlbumListFragment extends BaseLazyFragment<AlbumListPresenter> implements AlbumListContact.View, MediaControllerInterface, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private AlbumListAdapter mAlbumListAdapter;
    private List<AlbumBean> mAlbumBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_media;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAlbumListAdapter = new AlbumListAdapter(R.layout.item_adapter_album, mAlbumBeanList);
        mAlbumListAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(mAlbumListAdapter);
        mAlbumListAdapter.setOnItemClickListener(this);
        mAlbumListAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onLazyLoad() {
        mPresenter.getLocalAlbum();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SongsListActivity.launchFrom(getActivity(),String.valueOf(mAlbumBeanList.get(position).getId()), MediaDataType.TYPE_ALBUM,mAlbumBeanList.get(position).getAlbum());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void updateMiniPlayerBarState(boolean isShow) {
        mAlbumListAdapter.showMiniPlayerBarStub(getActivity(),isShow);
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        if (isAdded() && !TextUtils.isEmpty(metadata.getDescription().getMediaId())) {
            mAlbumListAdapter.setCurrentAlbum(metadata.getDescription().getDescription().toString());
            mAlbumListAdapter.showMiniPlayerBarStub(getActivity(),true);
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
    public void onLoadAlbumSuccess(List<AlbumBean> list) {
        mAlbumBeanList.clear();
        mAlbumBeanList.addAll(list);
        mAlbumListAdapter.notifyDataSetChanged();
    }


}
