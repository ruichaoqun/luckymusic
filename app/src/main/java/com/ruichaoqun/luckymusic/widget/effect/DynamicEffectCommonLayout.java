package com.ruichaoqun.luckymusic.widget.effect;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DynamicEffectCommonLayout extends FrameLayout {

    /* renamed from: onColorGet  reason: collision with root package name */
    public static final int f11714a = 0;

    /* renamed from: b  reason: collision with root package name */
    public static final int f11715b = -1;

    /* renamed from: c  reason: collision with root package name */
    public static final int f11716c = -3;

    /* renamed from: d  reason: collision with root package name */
    protected View mArtView;

    /* renamed from: e  reason: collision with root package name */
    protected o f11718e;

    /* renamed from: f  reason: collision with root package name */
    protected int f11719f;

    /* renamed from: g  reason: collision with root package name */
    private int f11720g;

    /* renamed from: h  reason: collision with root package name */
    private d f11721h;

    /* renamed from: i  reason: collision with root package name */
    private d.a f11722i;
    private ObjectAnimator j;
    private long k;

    public DynamicEffectCommonLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public DynamicEffectCommonLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DynamicEffectCommonLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f11719f = -1;
        this.f11720g = -1;
    }

    /**
     * 添加封面ImageView
     * @param view 
     * @param width
     * @param height
     */
    public void a(View view, int width, int height) {
        if (mArtView != view) {
            if (mArtView != null && mArtView.getParent() == this) {
                removeView(this.mArtView);
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
            layoutParams.gravity = Gravity.CENTER;
            addView(view, layoutParams);
            this.mArtView = view;
        }
    }

    public View getArtView() {
        return this.mArtView;
    }

    public int a(o oVar) {
        int i2 = 0;
        if (this.f11718e != oVar) {
            d dVar = this.f11721h;
            boolean z = dVar != null && dVar.b();
            b();
            d dVar2 = this.f11721h;
            d dVar3 = null;
            if (dVar2 != null) {
                dVar2.a((d.a) null, 0, false, false);
            }
            o oVar2 = this.f11718e;
            if (oVar2 != null) {
                removeView((View) oVar2);
            }
            if (this.mArtView != null) {
                Pair<Integer, Integer> d2 = oVar.d();
                if (((Integer) d2.first).intValue() > 0 && ((Integer) d2.second).intValue() > 0) {
                    ViewGroup.LayoutParams layoutParams = this.mArtView.getLayoutParams();
                    layoutParams.width = ((Integer) d2.first).intValue();
                    layoutParams.height = ((Integer) d2.second).intValue();
                }
            }
            addView((View) oVar, 0, new FrameLayout.LayoutParams(-1, -1));
            d dVar4 = this.f11721h;
            if (dVar4 != null) {
                o oVar3 = this.f11718e;
                if (oVar3 == null || oVar3.b(dVar4) != oVar.b(this.f11721h)) {
                    this.f11721h.a();
                    try {
                        dVar3 = this.f11721h.f();
                        dVar3.a(oVar.b(dVar3));
                    } catch (Throwable th) {
                        th.printStackTrace();
                        i2 = (!(th instanceof RuntimeException) || !th.getMessage().contains("-3")) ? -1 : -3;
                    }
                    this.f11721h = dVar3;
                }
                d dVar5 = this.f11721h;
                if (dVar5 != null) {
                    dVar5.a(this.f11722i, oVar.a(dVar5), oVar.c(), oVar.b());
                }
            }
            this.f11718e = oVar;
            this.f11718e.setColor(this.f11719f);
            if (z) {
                a();
            }
        }
        return i2;
    }

    public int a(int i2) {
        if (this.f11721h == null || this.f11720g != i2) {
            d dVar = this.f11721h;
            if (dVar != null) {
                dVar.a();
                this.f11721h = null;
            }
            try {
                this.f11721h = new c(i2);
                if (this.f11721h.b()) {
                    this.f11721h.a(false);
                }
                this.f11720g = i2;
                d();
            } catch (Throwable th) {
                th.printStackTrace();
                return (!(th instanceof RuntimeException) || !th.getMessage().contains("-3")) ? -1 : -3;
            }
        }
        return 0;
    }

    public void setVisualizer(d dVar) {
        d dVar2 = this.f11721h;
        if (dVar2 != dVar) {
            if (dVar2 != null) {
                dVar2.a();
                this.f11721h = null;
            }
            this.f11721h = dVar;
            d();
        }
    }

    private void d() {
        if (this.f11722i == null) {
            this.f11722i = new d.a() {
                public void a(Object obj, int i2) {
                    if (b.this.f11718e != null) {
                        b.this.f11718e.b(obj, i2);
                    }
                }

                public void b(Object obj, int i2) {
                    if (b.this.f11718e != null) {
                        b.this.f11718e.a(obj, i2);
                    }
                }
            };
        }
        o oVar = this.f11718e;
        if (oVar != null) {
            d dVar = this.f11721h;
            dVar.a(oVar.b(dVar));
            d dVar2 = this.f11721h;
            dVar2.a(this.f11722i, this.f11718e.a(dVar2), this.f11718e.c(), this.f11718e.b());
        }
    }

    public void a() {
        d dVar = this.f11721h;
        if (dVar != null) {
            dVar.a(true);
        }
        o oVar = this.f11718e;
        if (oVar != null) {
            oVar.ad_();
        }
    }

    public void b() {
        d dVar = this.f11721h;
        if (dVar != null) {
            dVar.a(false);
        }
        o oVar = this.f11718e;
        if (oVar != null) {
            oVar.a(true);
        }
    }

    public void c() {
        d dVar = this.f11721h;
        if (dVar != null) {
            dVar.e();
        }
        o oVar = this.f11718e;
        if (oVar != null) {
            oVar.a(false);
        }
    }

    public void a(boolean z) {
        View view = this.mArtView;
        if (view != null) {
            if (this.j == null) {
                this.j = ObjectAnimator.ofFloat(view, ROTATION, new float[]{0.0f, 360.0f}).setDuration(25000);
                this.j.setRepeatCount(-1);
                this.j.setInterpolator(new LinearInterpolator());
            }
            if (!this.j.isRunning()) {
                this.j.setCurrentPlayTime(z ? this.k : 0);
                this.j.start();
            }
        }
    }

    public void b(boolean z) {
        ObjectAnimator objectAnimator = this.j;
        if (objectAnimator != null) {
            this.k = objectAnimator.getCurrentPlayTime();
            this.j.cancel();
            if (z) {
                this.j.setCurrentPlayTime(0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        d dVar = this.f11721h;
        if (dVar != null) {
            dVar.a();
            this.f11721h = null;
        }
        super.onDetachedFromWindow();
    }

}
