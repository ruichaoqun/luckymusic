package com.ruichaoqun.luckymusic.ui.localmedia.fragment;

import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.fragment.BaseLazyFragment;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.media.MediaControllerInterface;
import com.ruichaoqun.luckymusic.media.MediaDataType;
import com.ruichaoqun.luckymusic.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020/1/6 15:39
 * description:
 */
public class LocalMediaFragment extends BaseLazyFragment<LocalMediaPresenter> implements MediaControllerInterface, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LocalMediaAdapter mLocalMediaAdapter;
    private List<MediaBrowserCompat.MediaItem> mMetadatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_media;
    }


    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocalMediaAdapter = new LocalMediaAdapter(R.layout.item_adapter_local_media, mMetadatas);
        mLocalMediaAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(mLocalMediaAdapter);
        mLocalMediaAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onLazyLoad() {
        if (getMediaBrowser() == null || !getMediaBrowser().isConnected()) {
            LogUtils.e(TAG, "媒体浏览器为空或媒体浏览器未连接媒体服务器！");
            return;
        }
        String parentId = new MediaID(MediaDataType.TYPE_SONG, 1).asString();
        getMediaBrowser().subscribe(parentId, new MediaBrowserCompat.SubscriptionCallback() {
            @Override
            public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
                mMetadatas.addAll(children);
                mLocalMediaAdapter.notifyDataSetChanged();
                //解除订阅
                getMediaBrowser().unsubscribe(parentId, this);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (isAdded()) {
            MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
            if (controller != null) {
                controller.getTransportControls().playFromMediaId(mLocalMediaAdapter.getItem(position).getMediaId(), null);
            }
        }
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        if (isAdded()) {
            MediaID id = MediaID.fromMediaMetaData(metadata);
            if (id != null && TextUtils.equals(id.getType(),MediaDataType.TYPE_SONG)) {
                mLocalMediaAdapter.setMediaWithId(id.asString());
                mLocalMediaAdapter.showMiniPlayerBarStub(getActivity(),true);
            }
        }
    }

    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {

    }
}
