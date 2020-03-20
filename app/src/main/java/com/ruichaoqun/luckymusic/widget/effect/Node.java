package com.ruichaoqun.luckymusic.widget.effect;

public class Node<T extends b<T>> {

    T currentData;

    T firstData;

    public void a(T t) {
        t.priviousData = this.currentData;
        if (this.currentData == null) {
            this.firstData = t;
        }
        this.currentData = t;
    }

    public void a(Node<T> nodeVar) {
        if (!nodeVar.b()) {
            T t = nodeVar.firstData;
            T t2 = this.currentData;
            t.priviousData = t2;
            if (t2 == null) {
                this.firstData = t;
            }
            this.currentData = nodeVar.currentData;
        }
    }

    public T a() {
        T t = this.currentData;
        if (t == null) {
            return null;
        }
        this.currentData = t.priviousData;
        if (this.currentData == null) {
            this.firstData = null;
        }
        t.priviousData = null;
        return t;
    }

    public boolean b() {
        return this.currentData == null;
    }

    public void c() {
        this.currentData = null;
        this.firstData = null;
    }
}
