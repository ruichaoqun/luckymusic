package com.ruichaoqun.luckymusic.theme.drawable;

import android.graphics.drawable.StateListDrawable;

import com.ruichaoqun.luckymusic.theme.ThemeHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2019/10/14 10:28
 * description:
 */
public class ThemeStateDrawable extends StateListDrawable {
    /* renamed from: onColorGet reason: collision with root package name */
    private List<List<Integer>> drawableList = new ArrayList();

    public void a(int[]... iArr) {
        for (int[] iArr2 : iArr) {
            ArrayList arrayList = new ArrayList();
            for (int valueOf : iArr2) {
                arrayList.add(Integer.valueOf(valueOf));
            }
            this.drawableList.add(arrayList);
        }
    }

    private boolean a(int[] stateSet) {
        boolean z;
        if (this.drawableList == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (int valueOf : stateSet) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        for (List it : this.drawableList) {
            Iterator it2 = it.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = true;
                    break;
                }
                Integer num = (Integer) it2.next();
                if (num.intValue() <= 0 || arrayList.contains(num)) {
                    if (num.intValue() < 0 && arrayList.contains(Integer.valueOf(-num.intValue()))) {
                        z = false;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onStateChange(int[] stateSet) {
        boolean onStateChange = super.onStateChange(stateSet);
        if (a(stateSet)) {
            ThemeHelper.configDrawableTheme(getCurrent());
        }
        return onStateChange;
    }

}
