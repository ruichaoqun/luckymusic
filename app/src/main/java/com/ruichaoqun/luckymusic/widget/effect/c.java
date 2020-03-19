package com.ruichaoqun.luckymusic.widget.effect;

public class c<T extends b<T>> {

    /* renamed from: a  reason: collision with root package name */
    T f11586a;

    /* renamed from: b  reason: collision with root package name */
    T f11587b;

    public void a(T t) {
        T t2 = this.f11586a;
        t.f11585a = t2;
        if (t2 == null) {
            this.f11587b = t;
        }
        this.f11586a = t;
    }

    public void a(c<T> cVar) {
        if (!cVar.b()) {
            T t = cVar.f11587b;
            T t2 = this.f11586a;
            t.f11585a = t2;
            if (t2 == null) {
                this.f11587b = t;
            }
            this.f11586a = cVar.f11586a;
        }
    }

    public T a() {
        T t = this.f11586a;
        if (t == null) {
            return null;
        }
        this.f11586a = t.f11585a;
        if (this.f11586a == null) {
            this.f11587b = null;
        }
        t.f11585a = null;
        return t;
    }

    public boolean b() {
        return this.f11586a == null;
    }

    public void c() {
        this.f11586a = null;
        this.f11587b = null;
    }
}
