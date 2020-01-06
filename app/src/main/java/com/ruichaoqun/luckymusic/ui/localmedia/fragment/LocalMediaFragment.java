package com.ruichaoqun.luckymusic.ui.localmedia.fragment;

import android.media.session.MediaController;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.fragment.BaseLazyFragment;
import com.ruichaoqun.luckymusic.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020/1/6 15:39
 * description:
 */
public class LocalMediaFragment extends BaseLazyFragment<LocalMediaPresenter> {
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
        mLocalMediaAdapter = new LocalMediaAdapter(R.layout.item_adapter_local_media,mMetadatas);
        mLocalMediaAdapter.setEmptyView(R.layout.layout_loading,recyclerView);
        recyclerView.setAdapter(mLocalMediaAdapter);
    }

    @Override
    protected void initData() {
        if(getMediaBrowser() == null || !getMediaBrowser().isConnected()){
            LogUtils.e(TAG,"媒体浏览器为空或媒体浏览器未连接媒体服务器！");
            return;
        }
        getMediaBrowser().subscribe();
    }
}
