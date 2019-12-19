package com.ruichaoqun.luckymusic.base.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;



import com.ruichaoqun.luckymusic.LuckyMusicApp;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2019/6/26 10:15
 * description:BaseFragmentStateAdapter
 */
public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> arrayList;
    private String[] titleStrs = null;
    private int[] titleInts = null;

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> arrayList, String[] titles) {
        super(fm);
        this.arrayList = arrayList;
        this.titleStrs = titles;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> arrayList, int[] titles) {
        super(fm);
        this.arrayList = arrayList;
        this.titleInts = titles;
    }

    @Override
    public int getCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        if (arrayList != null && arrayList.size() > position) {
            return arrayList.get(position);
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleStrs != null && titleStrs.length > position) {
            return titleStrs[position];
        }

        if (titleInts != null && titleInts.length > position) {
            return LuckyMusicApp.sInstance.getResources().getString(titleInts[position]);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(Object object) {
//        return super.getItemPosition(object);
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {//解决删除item时viewpager意外崩溃问题
            Log.e(this.getClass().getName(), "Catch the NullPointerException in FragmentPagerAdapter.finishUpdate: ");
        }
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        super.startUpdate(container);
    }
}
