package com.ruichaoqun.luckymusic.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2019/6/26 10:15
 * description:ViewGroup及其子类管理ItemView适配器
 */
public abstract class BaseCustomPageAdapter<T extends Object> {

    private Context context;
    private List<T> arrayList;
    private ViewGroup viewGroup;
    private OnItemClickListener listener;

    public BaseCustomPageAdapter(Context context, T[] arrays){
        this(context, Arrays.asList(arrays));
    }

    public BaseCustomPageAdapter(Context context, List<T> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    public int getItemCount(){
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public T getItem(int position){
        if (arrayList != null){
            return arrayList.get(position);
        }
        return null;
    }

    public Context getContext(){
        return context;
    }

    public abstract View getView(int position,View convertView, ViewGroup parent);

    /** 注册父类Layout */
    public void registerParentView(ViewGroup viewGroup){
        this.viewGroup = viewGroup;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        if (viewGroup == null){
            return;
        }

        viewGroup.removeAllViews();
        int count = getItemCount();
        for (int i = 0; i < count; i++){
            View itemView = BaseCustomPageAdapter.this.getView(i, null, null);
            if (listener != null){
                itemView.setOnClickListener(new ItemViewClick(i));
            }
            viewGroup.addView(itemView);
        }
    }

    private class ItemViewClick implements View.OnClickListener{

        private int position;

        public ItemViewClick(int positon){
            this.position = positon;
        }

        @Override
        public void onClick(View v) {
            listener.itemClick(position, v, getItem(position));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
        int countViews = viewGroup.getChildCount();
        for (int i = 0 ; i < countViews; i++){
            View itemView = viewGroup.getChildAt(i);
            itemView.setOnClickListener(new ItemViewClick(i));
        }
    }

    public interface OnItemClickListener{

        void itemClick(int position, View view, Object obj);
    }

}
