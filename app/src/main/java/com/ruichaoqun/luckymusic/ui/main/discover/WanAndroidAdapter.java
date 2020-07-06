package com.ruichaoqun.luckymusic.ui.main.discover;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.BannerListBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemType;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.List;

public class WanAndroidAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WanAndroidAdapter(List data) {
        super(data);
        addItemType(HomePageItemType.BANNER, R.layout.item_adapter_banner);
        addItemType(HomePageItemType.DATA, R.layout.item_adapter_home_page);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case HomePageItemType.BANNER:
                BannerListBean bannerListBean = (BannerListBean) item;
                if(ResourceRouter.getInstance().isRedTheme()){
                    helper.getView(R.id.iv_bac).setBackgroundColor(0xFFDB3F35);
                }else{
                    helper.getView(R.id.iv_bac).setBackgroundColor(Color.TRANSPARENT);
                }

                UltraViewPager ultraViewPager = helper.getView(R.id.ultra_viewpager);
                ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//                设定页面循环播放
                ultraViewPager.setInfiniteLoop(true);
                //设定页面自动切换  间隔2秒
                ultraViewPager.setAutoScroll(2000);
                BannerAdapter adapter = new BannerAdapter(bannerListBean.getList());
                ultraViewPager.setAdapter(adapter);
                ultraViewPager.refresh();
                break;
            case HomePageItemType.DATA:
                HomePageItemBean itemBean = (HomePageItemBean) item;
                helper.setText(R.id.tv_author, TextUtils.isEmpty(itemBean.getAuthor())?itemBean.getShareUser():itemBean.getAuthor());
                helper.setText(R.id.tv_title,itemBean.getTitle());
                helper.setText(R.id.tv_classify,itemBean.getSuperChapterName()+"·"+itemBean.getChapterName());
                helper.setImageResource(R.id.iv_collect,itemBean.isCollect()?R.mipmap.ic_collect:R.mipmap.ic_uncollect);
                break;
            default:
        }
    }
}
