package com.ruichaoqun.luckymusic.base.fragment;



import android.support.annotation.NonNull;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


/**
 * @author Rui Chaoqun
 * @date :2019/6/26 10:00
 * description:下拉刷新BaseFragment
 */
public abstract class BaseSwipeFragment<T extends IBasePresenter> extends BaseLazyFragment<T> {

    protected RefreshLayout refreshLayout;

    public void bindRefreshLayout(int refreshLayoutId){
        bindRefreshLayout((RefreshLayout) getView().findViewById(refreshLayoutId));
    }

    public void bindRefreshLayout(RefreshLayout refreshLayout){
        this.refreshLayout = refreshLayout;
        this.refreshLayout.setEnableHeaderTranslationContent(false);
        this.refreshLayout.setEnableLoadMore(false);
//        this.refreshLayout.setPrimaryColorsId(R.color.color_ff3a3a);
        this.refreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                BaseSwipeFragment.this.onRefresh();
            }
        });
    }

    /**
     * 刷新时回调
     */
    public abstract void onRefresh();

    /** 开启刷新 */
    public void startRefresh(){
        if (refreshLayout != null){
            refreshLayout.autoRefresh();
        }
    }

    /** 关闭刷新 */
    public void stopRefresh(){
        if (this.refreshLayout != null){
            this.refreshLayout.finishRefresh();
        }
    }
}
