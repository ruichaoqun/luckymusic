package com.ruichaoqun.luckymusic.widget.effect;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class a<T extends b<T>> extends Node<T> implements Iterable<T>{
    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new C0139a(this.currentData);
    }

    public void a(T t, T t2, T t3) {
        if (t != null) {
            t.priviousData = t3;
        } else {
            this.currentData = t3;
        }
        if (t2 == this.firstData) {
            this.firstData = t;
        }
        t2.priviousData = null;
    }

    private class C0139a implements Iterator<T> {

        private T f11582b;

        private T f11583c;

        private T f11584d;

        C0139a(T t) {
            this.f11582b = t;
        }

        @Override
        public boolean hasNext() {
            return this.f11582b != null;
        }

        @Override
        public T next() {
            T t = this.f11582b;
            this.f11584d = this.f11583c;
            this.f11583c = t;
            this.f11582b = t.priviousData;
            return t;
        }

        @Override
        public void remove() {
            a.this.a(this.f11584d, this.f11583c, this.f11582b);
            this.f11583c = this.f11584d;
        }
    }

}
