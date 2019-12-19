package com.ruichaoqun.luckymusic.base.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;


import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Objects;


/**
 * @author Rui Chaoqun
 * @date :2019/6/26 10:21
 * description:下拉刷新及上拉刷新需求fragment
 */
public abstract class BaseSwipeMoreTableFragment<R,T extends IBasePresenter> extends BaseSwipeTableFragment<R,T>{
    //页数
    protected int page = 1;
    //每页数据量
    protected int pageSize = 10;

    /**
     * 绑定RecyclerView
     * @param recyclerId  id
     * @param adapter adapter
     */
    @Override
    public void bindSwipeRecycler(int recyclerId, RecyclerView.Adapter adapter) {
        bindSwipeMoreRecycler(recyclerId,new LinearLayoutManager(getContext()), adapter);
    }

    /**
     * 绑定RecyclerView
     * @param recyclerId  id
     * @param adapter adapter
     */
    @Override
    public void bindSwipeRecycler(int recyclerId, RecyclerView.LayoutManager lm, RecyclerView.Adapter adapter) {
        bindSwipeMoreRecycler(recyclerId, lm, adapter);
    }

    /**
     * 绑定指定LayoutManager的RecyclerView
     * @param recyclerId id
     * @param lm LayoutManager
     * @param adapter adapter
     */
    private void bindSwipeMoreRecycler(int recyclerId, RecyclerView.LayoutManager lm, RecyclerView.Adapter adapter){
        super.bindSwipeRecycler(recyclerId, lm, adapter);
        if (this.refreshLayout !=null) {
            this.refreshLayout.setEnableLoadMore(true);
            this.refreshLayout.setRefreshFooter(new ClassicsFooter(Objects.requireNonNull(getContext())));
            this.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                    page++;
                    BaseSwipeMoreTableFragment.this.loadMore();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                    page = 1;
                    BaseSwipeMoreTableFragment.this.onRefresh();
                }
            });
        }else{
            Log.w("BaseSwipeMoreTable", "先调用bindRefreshLayout()方法！");
        }
    }

    /**
     * 加载更多
     */
    public abstract void loadMore();

    /**
     * 开启刷新
     */
    @Override
    public void startRefresh() {
        super.startRefresh();
    }

    /**
     * 关闭刷新
     */
    @Override
    public void stopRefresh() {
        //关闭顶部刷新
        super.stopRefresh();
        //关闭底部刷新
        if (this.refreshLayout != null){
            this.refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //如果数据量小于页数*每页数据量，判定为已经没有更多数据了，关闭上拉加载
        if(arrayList.size() < page*pageSize){
            setLoadMoreState(false);
        }else{
            setLoadMoreState(true);
        }
        //某些场景会自动刷新页面，刷新完毕后需要把recyclerView滑到最开始位置
        if(page == 1 && arrayList.size() > 0){
            recyclerView.scrollToPosition(0);
        }
    }

    /**
     * 刷新完毕之后，如果数据集合为空，禁止上拉加载，否则开启上拉加载
     * @param isLoadMore 是否允许加载更多
     */
    public void setLoadMoreState(boolean isLoadMore){
        if (this.refreshLayout != null) {
            this.refreshLayout.setEnableLoadMore(isLoadMore);
        }
    }
}
