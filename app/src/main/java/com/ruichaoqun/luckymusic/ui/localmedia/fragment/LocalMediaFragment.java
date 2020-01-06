package com.ruichaoqun.luckymusic.ui.localmedia.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.fragment.BaseLazyFragment;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_media;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLocalMediaAdapter = new LocalMediaAdapter();
        mLocalMediaAdapter.setEmptyView();
        recyclerView.setAdapter();
    }

    @Override
    protected void initData() {

    }
}
