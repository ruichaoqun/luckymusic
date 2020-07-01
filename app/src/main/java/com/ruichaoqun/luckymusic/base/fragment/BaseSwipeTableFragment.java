package com.ruichaoqun.luckymusic.base.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2019/6/26 10:15
 * description:SwipeTable基类去除上拉加载功能，作为普通的列表展示框体使用
 */
public abstract class BaseSwipeTableFragment<R,T extends IBasePresenter> extends BaseSwipeFragment<T> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener, BaseQuickAdapter.OnItemChildClickListener {

    protected RecyclerView recyclerView;
    protected List<R> arrayList = new ArrayList<>();
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.ItemDecoration mItemDecoration;

    /**
     * 绑定RecyclerView
     * @param recyclerId  id
     * @param adapter adapter
     */
    public void bindSwipeRecycler(int recyclerId, RecyclerView.Adapter adapter){
        bindSwipeRecycler(recyclerId, new LinearLayoutManager(getContext()), adapter);
    }

    /**
     * 绑定指定LayoutManager的RecyclerView
     * @param recyclerId id
     * @param lm LayoutManager
     * @param adapter adapter
     */
    public void bindSwipeRecycler(int recyclerId, RecyclerView.LayoutManager lm, RecyclerView.Adapter adapter){
        recyclerView = getView().findViewById(recyclerId);

        this.adapter = adapter;
        if (adapter instanceof BaseQuickAdapter) {
            ((BaseQuickAdapter) adapter).setOnItemClickListener(this);
            ((BaseQuickAdapter) adapter).setOnItemLongClickListener(this);
            ((BaseQuickAdapter) adapter).setOnItemChildClickListener(this);
        }

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mItemDecoration = itemDecoration;
    }


    /** 刷新适配器 */
    public void notifyDataSetChanged() {
        //无数据时取消ItemDecoration设置，有数据时再设置
        if(arrayList.isEmpty()){
            int count = recyclerView.getItemDecorationCount();
            for (int i = 0; i < count; i++) {
                recyclerView.removeItemDecorationAt(i);
            }
        }else{
            if(recyclerView.getItemDecorationCount() == 0 && mItemDecoration != null){
                recyclerView.addItemDecoration(mItemDecoration);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 刷新指定item
     * @param position position
     */
    public void notifyItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    /**
     * 指定位置新增item
     * @param position position
     */
    public void notifyItemInserted(int position) {
        adapter.notifyItemInserted(position);
    }

    /**
     * 指定位置删除item
     * @param position position
     */
    public void notifyItemRemoved(int position) {
        adapter.notifyItemRemoved(position);
        //删除之后需要刷新从刷新位置到列表最后的位置
        adapter.notifyItemRangeChanged(position,arrayList.size()-1);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void applyCurrentTheme() {
        super.applyCurrentTheme();

    }
}
