package com.ruichaoqun.luckymusic.widget.effect;

public class Node<T extends b<T>> {

    T currentData;

    T firstData;

    public void addData(T data) {
        data.priviousData = this.currentData;
        if (this.currentData == null) {
            this.firstData = data;
        }
        this.currentData = data;
    }

    public void addData(Node<T> nodeVar) {
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

    public T addData() {
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
