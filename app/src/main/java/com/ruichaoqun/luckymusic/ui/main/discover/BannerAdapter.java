package com.ruichaoqun.luckymusic.ui.main.discover;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private List<BannerItemBean> list;

    public BannerAdapter(List<BannerItemBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_banner,null);
        ImageView imageView = view.findViewById(R.id.iv_banner);
        BannerItemBean itemBean = list.get(position);
        Glide.with(imageView.getContext()).load(itemBean.getImagePath()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
