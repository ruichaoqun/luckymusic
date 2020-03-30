package com.ruichaoqun.luckymusic.widget.effect;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EffectData<T extends ListNode<T>> implements Iterable<T>{
    private T data;
    private T mEndData;
    private T cacheData;

    public void addData(T t){
        if(this.data == null){
            this.data = t;
            this.mEndData = t;
            return;
        }
        t.next = this.data;
        this.data = t;
    }

    public T getCacheData(){
        if(cacheData != null){
            T t = cacheData;
            cacheData = cacheData.next;
            t.next = null;
            return t;
        }
        return null;
    }

    public boolean isEmpty(){
        return data == null;
    }



    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new Itr(data);
    }

    public void clear() {
        if(mEndData != null){
            mEndData.next = cacheData;
            cacheData = data;
        }
        data = null;
        mEndData = null;
    }


    private class Itr implements Iterator<T> {
        private T data;
        private T next;
        private T privious;

        public Itr(T data) {
            this.data = data;
        }

        @Override
        public boolean hasNext() {
            return data != null;
        }

        @Override
        public T next() {
            T t = this.data;
            this.privious = this.next;
            this.next = t;
            this.data = data.next;
            return t;
        }

        @Override
        public void remove() {
            if(this.privious == null){
                mEndData.next =  EffectData.this.cacheData;
                EffectData.this.cacheData = next;
                data = null;
                EffectData.this.data = null;
                EffectData.this.mEndData = null;
            }else{
                EffectData.this.mEndData.next =  EffectData.this.cacheData;
                EffectData.this.cacheData = next;
                this.privious.next = null;
                data = null;
                EffectData.this.mEndData = privious;
            }
        }
    }
}
