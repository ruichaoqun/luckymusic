package com.ruichaoqun.luckymusic.widget.effect;

import java.util.Iterator;

public class a<T extends b<T>> extends c<T> implements Iterable<T>{
    public Iterator<T> iterator() {
        return new C0139a(this.f11586a);
    }

    /* access modifiers changed from: package-private */
    public void a(T t, T t2, T t3) {
        if (t != null) {
            t.f11585a = t3;
        } else {
            this.f11586a = t3;
        }
        if (t2 == this.f11587b) {
            this.f11587b = t;
        }
        t2.f11585a = null;
    }

    /* renamed from: com.netease.cloudmusic.module.ag.a.a$a  reason: collision with other inner class name */
    /* compiled from: ProGuard */
    private class C0139a implements Iterator<T> {

        /* renamed from: b  reason: collision with root package name */
        private T f11582b;

        /* renamed from: c  reason: collision with root package name */
        private T f11583c;

        /* renamed from: d  reason: collision with root package name */
        private T f11584d;

        C0139a(T t) {
            this.f11582b = t;
        }

        public boolean hasNext() {
            return this.f11582b != null;
        }

        /* renamed from: a */
        public T next() {
            T t = this.f11582b;
            this.f11584d = this.f11583c;
            this.f11583c = t;
            this.f11582b = t.f11585a;
            return t;
        }

        public void remove() {
            a.this.a(this.f11584d, this.f11583c, this.f11582b);
            this.f11583c = this.f11584d;
        }
    }

}
