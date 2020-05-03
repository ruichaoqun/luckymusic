package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

public class EqualizerCurveView extends View {

    /* renamed from: a  reason: collision with root package name */
    private List<Float> f15004a;

    /* renamed from: b  reason: collision with root package name */
    private List<Float> f15005b;

    /* renamed from: c  reason: collision with root package name */
    private Paint f15006c;

    /* renamed from: d  reason: collision with root package name */
    private Path f15007d;

    /* renamed from: e  reason: collision with root package name */
    private boolean f15008e;

    public EqualizerCurveView(Context context) {
        this(context,  null, 0);
    }

    public EqualizerCurveView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EqualizerCurveView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f15004a = new ArrayList();
        this.f15008e = false;
        a(context, attributeSet, i2);
    }

    private void a(Context context, AttributeSet attributeSet, int i2) {
        this.f15007d = new Path();
        this.f15006c = new Paint();
        this.f15006c.setColor(ContextCompat.getColor(getContext(),R.color.themeColor));
        this.f15006c.setAntiAlias(true);
        this.f15006c.setStrokeWidth((float) UiUtils.dp2px(1.0f));
        this.f15006c.setStyle(Paint.Style.STROKE);
    }

    public void setData(List<Float> list) {
        if (list != null && list.size() != 0) {
            this.f15005b = list;
            this.f15008e = true;
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f15008e) {
            a();
        }
        a(canvas);
    }

    private void a() {
        this.f15008e = false;
        this.f15004a.clear();
        for (int i2 = 0; i2 < this.f15005b.size(); i2++) {
            this.f15004a.add(Float.valueOf(((-this.f15005b.get(i2).floatValue()) / 3.0f) * ((float) getIntervalVert())));
        }
    }

    private void a(Canvas canvas) {
        int i2;
        this.f15007d.reset();
        int intervalHori = getIntervalHori();
        this.f15007d.moveTo(0.0f, (float) (getHeight() / 2));
        int size = this.f15004a.size();
        int i3 = 0;
        while (true) {
            i2 = size - 1;
            if (i3 >= i2) {
                break;
            }
            int i4 = i3 + 1;
            int i5 = intervalHori * i4;
            this.f15007d.quadTo((float) (i5 - (intervalHori / 2)), this.f15004a.get(i3).floatValue() + ((float) (getHeight() / 2)), (float) i5, this.f15004a.get(i3).floatValue() + ((this.f15004a.get(i4).floatValue() - this.f15004a.get(i3).floatValue()) / 2.0f) + ((float) (getHeight() / 2)));
            i3 = i4;
        }
        if (i2 >= 0 && i2 < size) {
            int i6 = (i2 + 1) * intervalHori;
            this.f15007d.quadTo((float) (i6 - (intervalHori / 2)), this.f15004a.get(i2).floatValue() + ((float) (getHeight() / 2)), (float) i6, this.f15004a.get(i2).floatValue() + ((((float) (getHeight() / 2)) - this.f15004a.get(i2).floatValue()) / 2.0f) + ((float) (getHeight() / 4)));
        }
        canvas.drawPath(this.f15007d, this.f15006c);
    }

    private int getIntervalVert() {
        return getHeight() / 8;
    }

    private int getIntervalHori() {
        return getWidth() / 9;
    }
}