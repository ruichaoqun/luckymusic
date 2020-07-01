package com.ruichaoqun.luckymusic.base.fragment;



import androidx.annotation.NonNull;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.widget.CustomMaterialHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Objects;


/**
 * @author Rui Chaoqun
 * @date :2019/6/26 10:00
 * description:下拉刷新BaseFragment
 */
public abstract class BaseSwipeFragment<T extends IBasePresenter> extends BaseLazyFragment<T> {

    protected RefreshLayout refreshLayout;
    protected CustomMaterialHeader mMaterialHeader;

    public void bindRefreshLayout(int refreshLayoutId){
        bindRefreshLayout((RefreshLayout) getView().findViewById(refreshLayoutId));
    }

    public void bindRefreshLayout(RefreshLayout refreshLayout){
        this.refreshLayout = refreshLayout;
        this.refreshLayout.setEnableHeaderTranslationContent(false);
        this.refreshLayout.setEnableLoadMore(false);
        mMaterialHeader = new CustomMaterialHeader(Objects.requireNonNull(getContext()));
        this.refreshLayout.setRefreshHeader(mMaterialHeader);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                BaseSwipeFragment.this.onRefresh();
            }
        });
    }

    protected void applyCurrentTheme() {
        if(mMaterialHeader != null){
            mMaterialHeader.onThemeReset();
        }
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
