package com.ruichaoqun.luckymusic.base.adapter.pager;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2019/6/26 10:15
 * description:ViewPagerAdapter基类
 */
public class ViewPagerAdapter<T extends View> extends PagerAdapter {

    private List<T> arrayList;

    public ViewPagerAdapter(List<T> arrayList) {
        super();
        this.arrayList = arrayList;
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= arrayList.size();
        if (position<0){
            position = arrayList.size()+position;
        }
        T view = arrayList.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }

    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //container.removeView(arrayList.get(position%arrayList.size()));
    }

    @Override
    public int getCount() {
        if(arrayList == null){
            return 0;
        }
        if(arrayList.size() == 1 || arrayList.size() == 2){
            return arrayList.size();
        }
        return Short.MAX_VALUE;
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
