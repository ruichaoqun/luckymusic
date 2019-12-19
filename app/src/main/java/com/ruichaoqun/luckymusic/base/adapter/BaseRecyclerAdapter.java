package com.ruichaoqun.luckymusic.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2019/6/18 14:06
 * description: RecyclerView.Adapter封装基类
 */
public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder, S> extends RecyclerView.Adapter<T> {
    private Context context;
    /**
     * 数据集合
     */
    private List<S> arrayList;

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    protected OnItemChildClickListener mOnItemChildClickListener;
    //数据为空时的展示View id
    protected @LayoutRes
    int mEmptyLayoutRes;

    public BaseRecyclerAdapter(Context context, S[] arrays){
        this.context = context;
        //注意！如果只使用Arrays.asList(arrays)做转换，后面调用list.remove(repeatPosition)或者list.add(0,keyword)会报异常UnsupportedOperationException，
        //原因是Arrays.asList生成的list并不是我们常用的java.util.ArrayList类，而是Arrays的一个内部类Arrays.ArrayList.
        //这个内部ArrayList是没有重写AbstractList的add以及remove方法的！
        //解决方式：转换成java.util.ArrayList
        arrayList = new ArrayList<>(Arrays.asList(arrays));
    }

    public BaseRecyclerAdapter(Context context, List<S> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public BaseRecyclerAdapter(Context context, List<S> arrayList,@LayoutRes int emptyLayoutRes){
        this.context = context;
        this.arrayList = arrayList;
        this.mEmptyLayoutRes = emptyLayoutRes;
    }

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 1 && mEmptyLayoutRes != 0){
            itemView = LayoutInflater.from(context).inflate(mEmptyLayoutRes, parent, false);
        }else{
            itemView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
        }
        return createNewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 && mEmptyLayoutRes != 0 && (arrayList == null || arrayList.size() == 0)){
            return 1;
        }
        return super.getItemViewType(position);
    }

    /**
     * 获取布局layout
     * @return layoutId
     */
    public abstract int getLayoutId();

    /**
     * 创建holder
     * @param itemView itemView
     * @return holder
     */
    public abstract T createNewHolder(View itemView);

    /**
     ****************************************************************************
     *                      不要复写这个函数！使用bindHolder来绑定数据          *
     ****************************************************************************
     */
    @Override
    public void onBindViewHolder(@NonNull final T holder, @SuppressLint("RecyclerView") final int position) {
        //单独处理空数据展示item
        if(position == 0 && mEmptyLayoutRes != 0 && (arrayList == null || arrayList.size() == 0)){
            return;
        }
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, v, position);
                }
            });
        }

        if (mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(holder, v, position);
                    return false;
                }
            });
        }
        bindHolder(holder,position);
    }

    /**
     * 绑定数据
     * @param holder 绑定holder
     * @param position 当前item的position
     */
    public abstract void bindHolder(T holder, final int position);

    public List<S> getArrayList() {
        return arrayList;
    }

    @Override
    public int getItemCount() {
        if(arrayList == null || arrayList.size() == 0){
            if(mEmptyLayoutRes != 0){
                return 1;
            }
           return 0;
        }
        return arrayList.size();
    }

    public S getItem(int position){
        if (arrayList != null && arrayList.size() > position){
            return arrayList.get(position);
        }
        return null;
    }

    public Context getContext(){
        return context;
    }

    public AppCompatActivity getActivity(){
        if (context instanceof AppCompatActivity){
            return (AppCompatActivity)context;
        }

        return null;
    }

    public void setItem(S info, int position){
        arrayList.set(position, info);
        notifyItemChanged(position);
    }

    public int getColor(int colorId){
        return ContextCompat.getColor(context, colorId);
    }

    public String getString(int stringId){
        return context.getResources().getString(stringId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemClicklongListener) {
        this.mOnItemLongClickListener = onItemClicklongListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * item点击接口
     */
    public interface OnItemClickListener {
        /**
         *
         * @param viewHolder viewHolder
         * @param view itemView
         * @param position position
         */
        void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

    /**
     * item长按接口
     */
    public interface OnItemLongClickListener {
        /**
         *
         * @param viewHolder viewHolder
         * @param view itemView
         * @param position position
         */
        void onItemLongClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

    /**
     * item中的child点击接口
     */
    public interface OnItemChildClickListener {
        /**
         *
         * @param viewHolder viewHolder
         * @param view childView
         * @param position childView所在的item的position
         */
        void onItemChildClick(RecyclerView.ViewHolder viewHolder, View view, int position);
    }

}
